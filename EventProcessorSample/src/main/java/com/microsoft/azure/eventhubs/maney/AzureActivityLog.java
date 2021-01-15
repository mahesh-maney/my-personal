package com.microsoft.azure.eventhubs.maney;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class AzureActivityLog {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date time;
    private String resourceId;
    private String operationName;
    private String category;
    private String resultType;
    private String resultSignature;
    private String durationMs;
    private String callerIpAddress;
    private String correlationId;
    private Identity identity;
    private String level;
    private Properties properties;
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getResultSignature() {
        return resultSignature;
    }

    public void setResultSignature(String resultSignature) {
        this.resultSignature = resultSignature;
    }

    public String getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(String durationMs) {
        this.durationMs = durationMs;
    }

    public String getCallerIpAddress() {
        return callerIpAddress;
    }

    public void setCallerIpAddress(String callerIpAddress) {
        this.callerIpAddress = callerIpAddress;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}