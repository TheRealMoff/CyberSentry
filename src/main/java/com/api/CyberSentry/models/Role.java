package com.api.CyberSentry.models;

import com.api.CyberSentry.enums.ApiUserRole;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private ApiUserRole role;

    public Role() {
    }

    public Role(Long id, ApiUserRole role) {
        this.id = id;
        this.role = role;
    }

    public Role(ApiUserRole role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApiUserRole getRole() {
        return role;
    }

    public void setRole(ApiUserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role=" + role +
                '}';
    }
}
