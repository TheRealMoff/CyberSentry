package com.api.CyberSentry.dto;

import com.api.CyberSentry.models.User;

public class IncidentDTO {

    private Long id;
    private String title;
    private String description;
    private String affectedSystems;
    private String priority;
    private String status;
    private String username; //username from the User class

    public IncidentDTO() {
    }

    public IncidentDTO(Long id,
                       String title,
                       String description,
                       String affectedSystems,
                       String priority,
                       String status,
                       String username) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.affectedSystems = affectedSystems;
        this.priority = priority;
        this.status = status;
        this.username = username;
    }

    public IncidentDTO(String title,
                       String description,
                       String affectedSystems,
                       String priority,
                       String status,
                       String username) {
        this.title = title;
        this.description = description;
        this.affectedSystems = affectedSystems;
        this.priority = priority;
        this.status = status;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAffectedSystems() {
        return affectedSystems;
    }

    public void setAffectedSystems(String affectedSystems) {
        this.affectedSystems = affectedSystems;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "IncidentDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", affectedSystems='" + affectedSystems + '\'' +
                ", priority='" + priority + '\'' +
                ", status='" + status + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
