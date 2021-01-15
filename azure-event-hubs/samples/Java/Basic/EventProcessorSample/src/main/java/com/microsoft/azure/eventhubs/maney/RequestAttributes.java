package com.microsoft.azure.eventhubs.maney;

import java.util.Date;
import java.util.List;

public class RequestAttributes {

    private List auth;

    public List getAuth() {
        return auth;
    }

    public void setAuth(List auth) {
        this.auth = auth;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    private Date time;

}
