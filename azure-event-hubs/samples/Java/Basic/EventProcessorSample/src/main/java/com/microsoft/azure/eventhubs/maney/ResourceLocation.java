package com.microsoft.azure.eventhubs.maney;

import java.util.List;

public class ResourceLocation {
    public List<String> getCurrentLocations() {
        return currentLocations;
    }

    public void setCurrentLocations(List<String> currentLocations) {
        this.currentLocations = currentLocations;
    }

    private List<String> currentLocations;
}
