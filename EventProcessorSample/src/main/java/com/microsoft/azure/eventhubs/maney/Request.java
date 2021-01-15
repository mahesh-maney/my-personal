package com.microsoft.azure.eventhubs.maney;

public class Request {

    private String type;
    private String pageSizze;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPageSizze() {
        return pageSizze;
    }

    public void setPageSizze(String pageSizze) {
        this.pageSizze = pageSizze;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    private String projectId;
    private String region;

}
