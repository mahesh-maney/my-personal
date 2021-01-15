package com.microsoft.azure.eventhubs.maney;

public class Identity {
    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }

    public Claims getClaims() {
        return claims;
    }

    public void setClaims(Claims claims) {
        this.claims = claims;
    }

    private Authorization authorization;
    private Claims claims;
}