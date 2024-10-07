package com.api.CyberSentry.service;

import com.api.CyberSentry.models.Incident;
import com.api.CyberSentry.models.User;
import com.api.CyberSentry.repository.IncidentRepository;
import com.api.CyberSentry.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;

    public IncidentService(IncidentRepository incidentRepository, UserRepository userRepository) {
        this.incidentRepository = incidentRepository;
        this.userRepository = userRepository;
    }

    //Adding a new incident
    public Incident addIncident(Incident incident){
        User incidentAssignee = userRepository.findByUsername(incident.getAssignee().getUsername());

        if (incidentAssignee != null) {
            incident.setAssignee(incidentAssignee);
            try {
                return incidentRepository.save(incident);
            } catch (Exception e) {
                throw new RuntimeException("Failed to add new incident " + e.getMessage());
            }
        } else {
            throw new UsernameNotFoundException("Username " + incident.getAssignee().getUsername() + "not found");
        }
    }

    //Get all incidents
    public List<Incident> getAllIncidents(){
        try {
            return incidentRepository.findAll();
        }
        catch (Exception e){
            throw new RuntimeException("Cannot retrieve all incidents " + e.getMessage());
        }
    }

    //Get a specific incident
    public Optional<Incident> getIncidentByTitle(String title){
        try{
            return getAllIncidents()
                    .stream()
                    .filter(Incident -> title.equals(Incident.getTitle()))
                    .findFirst();
        }
        catch (Exception e){
            throw new RuntimeException("Incident " + e.getMessage() + "not found");
        }
    }

    //Update incident
    public Optional<Incident> updateIncident(Long id, Incident incidentBeingUpdated){
        try{
            Optional<Incident> existingIncident = incidentRepository.findById(id);
            if (existingIncident.isPresent()){
                Incident existingIncident1 = existingIncident.get();
                existingIncident1.setTitle(incidentBeingUpdated.getTitle());
                existingIncident1.setDescription(incidentBeingUpdated.getDescription());
                existingIncident1.setAffectedSystems(incidentBeingUpdated.getAffectedSystems());
                existingIncident1.setPriority(incidentBeingUpdated.getPriority());
                existingIncident1.setStatus(incidentBeingUpdated.getStatus());
                existingIncident1.setAssignee(incidentBeingUpdated.getAssignee());

                Incident savedIncident = incidentRepository.save(incidentBeingUpdated);
                return Optional.of(savedIncident);
            }
            else {
                return Optional.empty();
            }
        }
        catch (Exception e){
            throw new RuntimeException("Failed to update incident " + e.getMessage());
        }
    }

    //Delete Incident
    public boolean deleteIncident(Long id){
        try {
            incidentRepository.deleteById(id);
            return true; //if successul
        }
        catch (Exception e){
            throw new RuntimeException("Failed to delete incident " + e.getMessage() + id);
        }
    }
}
