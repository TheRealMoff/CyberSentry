package com.api.CyberSentry.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "incident")
public class Incident {

    @Id
    @JsonProperty(value = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(value = "Title")
    private String title;

    @JsonProperty(value = "Description")
    private String description;

    @JsonProperty(value = "Affected Systems")
    private String affectedSystems;

    @JsonProperty(value = "Priority")
    private String priority;

    @JsonProperty(value = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "id")
    private User assignee;

    public Incident() {
    }

    public Incident(String title,
                    String description,
                    String affectedSystems,
                    String priority,
                    String status,
                    User assignee) {
        this.title = title;
        this.description = description;
        this.affectedSystems = affectedSystems;
        this.priority = priority;
        this.status = status;
        this.assignee = assignee;
    }

    public Incident(Long id,
                    String title,
                    String description,
                    String affectedSystems,
                    String priority,
                    String status,
                    User assignee) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.affectedSystems = affectedSystems;
        this.priority = priority;
        this.status = status;
        this.assignee = assignee;
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

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    @Override
    public String toString() {
        return "Incident{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", affectedSystems='" + affectedSystems + '\'' +
                ", priority='" + priority + '\'' +
                ", status='" + status + '\'' +
                ", assignee=" + assignee +
                '}';
    }
}
