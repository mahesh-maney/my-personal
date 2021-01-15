package com.microsoft.azure.eventhubs.maney;

import java.util.List;

public class ProtoPayload {

    private String type;
    private AuthenticationInfo authenticationInfo;
    private AuthorizationInfo authorizationInfo;
    private String methodName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AuthenticationInfo getAuthenticationInfo() {
        return authenticationInfo;
    }

    public void setAuthenticationInfo(AuthenticationInfo authenticationInfo) {
        this.authenticationInfo = authenticationInfo;
    }

    public AuthorizationInfo getAuthorizationInfo() {
        return authorizationInfo;
    }

    public void setAuthorizationInfo(AuthorizationInfo authorizationInfo) {
        this.authorizationInfo = authorizationInfo;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public RequestMetadata getRequestMetadata() {
        return requestMetadata;
    }

    public void setRequestMetadata(RequestMetadata requestMetadata) {
        this.requestMetadata = requestMetadata;
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public void setResourceLocation(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List getStatus() {
        return status;
    }

    public void setStatus(List status) {
        this.status = status;
    }

    private Request request;
    private RequestMetadata requestMetadata;
    private ResourceLocation resourceLocation;
    private String resourceName;
    private String serviceName;
    private List status;

}
