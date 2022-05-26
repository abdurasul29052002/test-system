package com.example.testsystem.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum AuthorityType implements GrantedAuthority {
    ADMIN,
    USER;
    @Override
    public String getAuthority() {
        return this.name();
    }
}
