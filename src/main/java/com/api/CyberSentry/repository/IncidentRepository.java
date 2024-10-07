package com.api.CyberSentry.repository;

import com.api.CyberSentry.models.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
}
