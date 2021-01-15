package com.microsoft.azure.eventhubs.maney;

public class Labels {

    private String cluster_name;

    public String getCluster_name() {
        return cluster_name;
    }

    public void setCluster_name(String cluster_name) {
        this.cluster_name = cluster_name;
    }

    public String getCluster_uuid() {
        return cluster_uuid;
    }

    public void setCluster_uuid(String cluster_uuid) {
        this.cluster_uuid = cluster_uuid;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    private String cluster_uuid;
    private String project_id;
    private String region;

}
