package com.staho.rest;

import javax.persistence.*;
import java.io.Serializable;


public class Client implements Serializable {

    private long id;
    private String name;
    private String firstName;
    private String uuid;

    public Client(String name, String firstName, String uuid, double amount) {
        this.name = name;
        this.firstName = firstName;
        this.uuid = uuid;
    }

    public Client() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
