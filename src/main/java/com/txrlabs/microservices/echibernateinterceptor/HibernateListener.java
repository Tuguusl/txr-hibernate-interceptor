package com.txrlabs.microservices.echibernateinterceptor;

import java.util.List;

public interface HibernateListener {

    void onCreate(ListenedEntity entity);
    void onDelete(ListenedEntity entity);
    void onUpdate(ListenedEntity oldEntity, ListenedEntity newEntity, List<String> changedProperties);

}
