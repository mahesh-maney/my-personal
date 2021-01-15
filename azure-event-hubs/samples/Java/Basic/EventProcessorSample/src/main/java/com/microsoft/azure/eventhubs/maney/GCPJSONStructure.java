package com.microsoft.azure.eventhubs.maney;

import java.util.Date;

public class GCPJSONStructure {

    public String getInsertId() {
        return insertId;
    }

    public void setInsertId(String insertId) {
        this.insertId = insertId;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public ProtoPayload getProtoPayload() {
        return protoPayload;
    }

    public void setProtoPayload(ProtoPayload protoPayload) {
        this.protoPayload = protoPayload;
    }

    public Date getReceiveTimestamp() {
        return receiveTimestamp;
    }

    public void setReceiveTimestamp(Date receiveTimestamp) {
        this.receiveTimestamp = receiveTimestamp;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    private String insertId;
    private String logName;
    private ProtoPayload protoPayload;
    private Date receiveTimestamp;
    private Resource resource;
    private String severity;
    private Date timestamp;

}
