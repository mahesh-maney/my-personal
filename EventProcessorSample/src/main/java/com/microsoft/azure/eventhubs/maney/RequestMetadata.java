package com.microsoft.azure.eventhubs.maney;

import java.util.List;

public class RequestMetadata {

    private String calleIP;
    private String callerSuppliedUserAgent;
    private List destinationAttributes;

    public String getCalleIP() {
        return calleIP;
    }

    public void setCalleIP(String calleIP) {
        this.calleIP = calleIP;
    }

    public String getCallerSuppliedUserAgent() {
        return callerSuppliedUserAgent;
    }

    public void setCallerSuppliedUserAgent(String callerSuppliedUserAgent) {
        this.callerSuppliedUserAgent = callerSuppliedUserAgent;
    }

    public List getDestinationAttributes() {
        return destinationAttributes;
    }

    public void setDestinationAttributes(List destinationAttributes) {
        this.destinationAttributes = destinationAttributes;
    }

    public RequestAttributes getRequestAttributes() {
        return requestAttributes;
    }

    public void setRequestAttributes(RequestAttributes requestAttributes) {
        this.requestAttributes = requestAttributes;
    }

    private RequestAttributes requestAttributes;

}
