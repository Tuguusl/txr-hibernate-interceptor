package com.txrlabs.microservices.echibernateinterceptor;

public interface EcListener {

    void onCreate(ListenedEntity entity);
    void onDelete(ListenedEntity entity);
    void onUpdate(ListenedEntity oldEntity, ListenedEntity newEntity);

}
