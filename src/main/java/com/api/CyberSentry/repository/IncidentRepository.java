package com.api.CyberSentry.repository;

import com.api.CyberSentry.models.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentRepository extends JpaRepository<Incident, Long> {

    List<Incident> findByPriority(String priority);
    List<Incident> findByStatus(String status);
    List<Incident> findByAssigneeUsername(String username);
}
