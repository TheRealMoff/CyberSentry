package com.api.CyberSentry.enums;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.api.CyberSentry.enums.ApiUserPermissions.*;

public enum ApiUserRole {

    ADMIN(Sets.newHashSet(INCIDENT_READ, INCIDENT_WRITE, USER_READ, USER_WRITE)),

    USER(Sets.newHashSet(INCIDENT_READ, USER_READ, USER_WRITE));

    //Define the actual permission
    private final Set<ApiUserPermissions> permissions;

    ApiUserRole(Set<ApiUserPermissions> permissions) {
        this.permissions = permissions;
    }

    public Set<ApiUserPermissions> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
