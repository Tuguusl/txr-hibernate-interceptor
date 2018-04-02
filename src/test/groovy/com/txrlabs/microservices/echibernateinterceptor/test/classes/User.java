package com.txrlabs.microservices.echibernateinterceptor.test.classes;

import com.txrlabs.microservices.echibernateinterceptor.ListenedEntity;

public class User extends ListenedEntity {

    public String name;
    private String lastName;
    public Integer age;
    Double salary;

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

    public User(String name, String lastName, Integer age, Double salary) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.salary = salary;
    }
}
