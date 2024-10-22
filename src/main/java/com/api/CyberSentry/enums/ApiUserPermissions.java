package com.api.CyberSentry.enums;

public enum ApiUserPermissions {

    INCIDENT_READ("incident:read"),
    INCIDENT_WRITE("incident:write"),
    USER_READ("user:read"),
    USER_WRITE("user:write");

    private final String permission;

    ApiUserPermissions(String permission) {
        this.permission = permission;
    }

    public String getPermission(){
        return permission;
    }
}
