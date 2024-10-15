package com.api.CyberSentry.service;

import com.api.CyberSentry.dto.IncidentDTO;
import com.api.CyberSentry.models.Incident;
import com.api.CyberSentry.models.User;
import com.api.CyberSentry.repository.IncidentRepository;
import com.api.CyberSentry.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    //Get incident by id
    public IncidentDTO getIncidentById(Long id) {
        Incident incident = incidentRepository.findById(id).orElse(null);
        return incident != null ? modelMapper.map(incident, IncidentDTO.class) : null;
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

    //Get a specific incident by Title
    //To be added*****
//    public Incident getIncidentByTitle(String title){
//        try {
//            return incidentRepository.findBy();
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to get user " + username + e.getMessage());
//        }
//    }

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
