package com.microsoft.azure.eventhubs.maney;

public class AuthenticationInfo {

    public String getPrincipalEmail() {
        return principalEmail;
    }

    public void setPrincipalEmail(String principalEmail) {
        this.principalEmail = principalEmail;
    }

    public String getServiceAccountKeyName() {
        return serviceAccountKeyName;
    }

    public void setServiceAccountKeyName(String serviceAccountKeyName) {
        this.serviceAccountKeyName = serviceAccountKeyName;
    }

    private String principalEmail;
    private String serviceAccountKeyName;

}
