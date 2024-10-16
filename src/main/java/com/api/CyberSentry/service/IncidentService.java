package com.api.CyberSentry.service;

import com.api.CyberSentry.dto.IncidentDTO;
import com.api.CyberSentry.models.Incident;
import com.api.CyberSentry.models.User;
import com.api.CyberSentry.repository.IncidentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public IncidentService(IncidentRepository incidentRepository,
                           UserService userService,
                           ModelMapper modelMapper) {

        this.incidentRepository = incidentRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    //Get all incidents
    public List<IncidentDTO> getAllIncidents() {

        return incidentRepository.findAll().stream()
                .map(incident -> modelMapper.map(incident, IncidentDTO.class))
                .collect(Collectors.toList());
    }

    //Create a new incident
    public IncidentDTO createIncident(IncidentDTO incidentDto) {

        User user = userService.getUserByUsername(incidentDto.getUsername());

        if (user == null) {
            throw new RuntimeException("User not found ");
        }

        Incident incident = modelMapper.map(incidentDto, Incident.class);

        incident.setTitle(incidentDto.getTitle());
        incident.setDescription(incidentDto.getDescription());
        incident.setAffectedSystems(incidentDto.getAffectedSystems());
        incident.setPriority(incidentDto.getPriority());
        incident.setStatus(incidentDto.getStatus());
        incident.setAssignee(user);

        Incident savedIncident = incidentRepository.save(incident);
        return modelMapper.map(savedIncident, IncidentDTO.class);
    }

    //Get incident by id
    public IncidentDTO getIncidentById(Long id) {

        Incident incident = incidentRepository.findById(id).orElse(null);
        return incident != null ? modelMapper.map(incident, IncidentDTO.class) : null;
    }

    //Get incident by priority
    public List<IncidentDTO> getIncidentsByPriority(String priority){

        List<Incident> incidents = incidentRepository.findByPriority(priority);

        return incidents.stream()
                .map(incident -> modelMapper.map(incident, IncidentDTO.class))
                .collect(Collectors.toList());
    }

    //Get incident by status
    public List<IncidentDTO> getIncidentsByStatus(String status){

        List<Incident> incidents = incidentRepository.findByStatus(status);

        return incidents.stream()
                .map(incident -> modelMapper.map(incident, IncidentDTO.class))
                .collect(Collectors.toList());
    }

    //Get incident by associated user
    public List<IncidentDTO> getIncidentsByUsername(String username) {

        List<Incident> incidents = incidentRepository.findByAssigneeUsername(username);
        return incidents.stream()
                .map(incident -> modelMapper.map(incident, IncidentDTO.class))
                .collect(Collectors.toList());
    }

    //Update incident
    public IncidentDTO updateIncident(Long id, IncidentDTO incidentDto) {

        Incident existingIncident = incidentRepository.findById(id).orElse(null);
        if (existingIncident == null) {
            return null;
        }
        User user = userService.getUserByUsername(incidentDto.getUsername());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        existingIncident.setTitle(incidentDto.getTitle());
        existingIncident.setDescription(incidentDto.getDescription());
        existingIncident.setAffectedSystems(incidentDto.getAffectedSystems());
        existingIncident.setPriority(incidentDto.getPriority());
        existingIncident.setStatus(incidentDto.getStatus());
        existingIncident.setAssignee(user);

        Incident updatedIncident = incidentRepository.save(existingIncident);
        return modelMapper.map(updatedIncident, IncidentDTO.class);
    }

    //Delete Incident
    public boolean deleteIncident(Long id) {

        if (incidentRepository.existsById(id)) {
            incidentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
