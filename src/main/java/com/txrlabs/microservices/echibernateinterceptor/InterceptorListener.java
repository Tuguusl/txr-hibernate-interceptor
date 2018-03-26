package com.txrlabs.microservices.echibernateinterceptor;

import org.hibernate.type.Type;

public interface InterceptorListener<T>{

    void onChange(Object[] previousState, Object[] currentState, String[] propertyName, Type[] types, Object entity);
    void onCreate(Object[] state, String[] propertyName, Type[] types, Object entity);
    void onDelete(Object[] state, String[] propertyName, Type[] types, Object entity);

}