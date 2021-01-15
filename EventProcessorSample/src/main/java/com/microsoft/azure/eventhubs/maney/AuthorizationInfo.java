package com.microsoft.azure.eventhubs.maney;

import java.util.List;

public class AuthorizationInfo {

    private String granted;
    private String permission;
    private List resourceAttributes;

    public String getGranted() {
        return granted;
    }

    public void setGranted(String granted) {
        this.granted = granted;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public List getResourceAttributes() {
        return resourceAttributes;
    }

    public void setResourceAttributes(List resourceAttributes) {
        this.resourceAttributes = resourceAttributes;
    }
}
