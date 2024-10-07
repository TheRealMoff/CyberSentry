package com.api.CyberSentry.service;

import com.api.CyberSentry.models.Incident;
import com.api.CyberSentry.models.User;
import com.api.CyberSentry.repository.IncidentRepository;
import com.api.CyberSentry.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
    
}
