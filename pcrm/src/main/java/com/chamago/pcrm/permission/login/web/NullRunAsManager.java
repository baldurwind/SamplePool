package com.chamago.pcrm.permission.login.web;

import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.intercept.RunAsManager;
import org.springframework.security.core.Authentication;

final class NullRunAsManager implements RunAsManager {
    //~ Methods ========================================================================================================

    public Authentication buildRunAs(Authentication authentication, Object object, Collection<ConfigAttribute> config) {
        return null;
    }

    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }
}