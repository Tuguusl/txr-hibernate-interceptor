package com.txrlabs.microservices.echibernateinterceptor.test.classes;


import com.txrlabs.microservices.echibernateinterceptor.ListenedEntity;

public class FrontEnd extends ListenedEntity {

    public String name;
    private String lastName;
    public Boolean admin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public FrontEnd(String name, String lastName, Boolean admin) {
        this.name = name;
        this.lastName = lastName;
        this.admin = admin;
    }
}
