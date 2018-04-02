package com.txrlabs.microservices.echibernateinterceptor.test.classes;

import com.txrlabs.microservices.echibernateinterceptor.ListenedEntity;

import java.util.List;

public class Group extends ListenedEntity {

    public List<Integer> departments;
    public List<User> users;
    public User manager;

    public Group(List<Integer> departments, List<User> users, User manager) {
        this.departments = departments;
        this.users = users;
        this.manager = manager;
    }
}
