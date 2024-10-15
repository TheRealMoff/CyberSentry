package com.api.CyberSentry.controller;

import com.api.CyberSentry.dto.IncidentDTO;
import com.api.CyberSentry.models.Incident;
import com.api.CyberSentry.service.IncidentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    //Retrieve all incidents
    @GetMapping("/incidents")
    public List<IncidentDTO> getAllIncidents() {
        return incidentService.getAllIncidents();
    }

    //Retrieve incident by id
    @GetMapping("/incidents/{id}")
    public ResponseEntity<IncidentDTO> getIncidentById(@PathVariable Long id) {
        IncidentDTO incident = incidentService.getIncidentById(id);

        if (incident != null) {
            return ResponseEntity.ok(incident);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Create a new incident
    @PostMapping("/add")
    public ResponseEntity<IncidentDTO> createIncident(@RequestBody IncidentDTO incidentDto) {
        try {
            IncidentDTO createdIncident = incidentService.createIncident(incidentDto);
            return ResponseEntity.ok(createdIncident);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    /*
    To add more retrieval end points as the project grows i.e. integration of front end

    - /incidents/{title}
    - /incidents/{description}
    - /incidents/{affectedSystem}
    - /incidents/{priority}
    - /incidents/{status}
    - /incidents/{assignedTo}

     */

    //Update Incident
    @PutMapping(path = "/incidents/{id}")
    public ResponseEntity<IncidentDTO> updateIncident(@PathVariable Long id,
                                                      @RequestBody IncidentDTO incidentDto) {

        try {
            IncidentDTO updatedIncident = incidentService.updateIncident(id, incidentDto);
            if (updatedIncident != null) {
                return ResponseEntity.ok(updatedIncident);

            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //Delete an incident
    @DeleteMapping(value = "/incidents/{id}")
    public ResponseEntity<String> deleteIncident(@PathVariable Long id){
        boolean deleteStatus = incidentService.deleteIncident(id);
        if (deleteStatus){
            return ResponseEntity.ok("Incident of id " + id + " has been successfully deleted");
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete incident of id " + id);
        }
    }
}
