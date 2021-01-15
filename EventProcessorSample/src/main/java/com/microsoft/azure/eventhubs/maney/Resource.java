package com.microsoft.azure.eventhubs.maney;

public class Resource {

    public Labels getLabels() {
        return labels;
    }

    public void setLabels(Labels labels) {
        this.labels = labels;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private Labels labels;
    private String type;

}
