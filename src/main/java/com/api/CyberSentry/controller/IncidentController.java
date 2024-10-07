package com.api.CyberSentry.controller;

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

    //Create a new incident
    @PostMapping("/addIncident")
    public ResponseEntity<Incident> addIncident(@RequestBody Incident incident){
        Incident savedIncident = incidentService.addIncident(incident);
        return ResponseEntity.ok(savedIncident);
    }

    //Retrieve a single incident
    @GetMapping("/incidents/{title}")
    public ResponseEntity<Incident> getIncidentByTitle(@PathVariable String title){
        Optional<Incident> incidentOptional = incidentService.getIncidentByTitle(title);
        return incidentOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    /*
    To add more retrieval end points as the project grows i.e. integration of front end

    - /incidents/{description}
    - /incidents/{affectedSystem}
    - /incidents/{priority}
    - /incidents/{status}
    - /incidents/{assignedTo}

     */

    //Retrieve all incidents
    @GetMapping("/incidents")
    public ResponseEntity<List<Incident>> getAllIncidents(){
        List<Incident> incidents = incidentService.getAllIncidents();
        return ResponseEntity.ok(incidents);
    }

    //Update Incident
    public ResponseEntity<Incident> updateIncident(@PathVariable Long id,
                                                   @RequestBody Incident incident){
        Optional<Incident> updatedIncidentOptional = incidentService.updateIncident(id,incident);
        return updatedIncidentOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Delete an incident
    public ResponseEntity<String> deleteIncident(@PathVariable Long id){
        boolean deleteStatus = incidentService.deleteIncident(id);
        if (deleteStatus){
            return ResponseEntity.ok("Incident of id " + id + "has been successfully deleted");
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete incident of id " + id);
        }
    }
}
