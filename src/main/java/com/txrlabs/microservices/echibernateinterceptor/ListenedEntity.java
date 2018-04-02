package com.txrlabs.microservices.echibernateinterceptor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.SerializationUtils;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class ListenedEntity implements Serializable{


    @Transient
    @JsonIgnore
    public transient Embedded _embedded = new Embedded();


    @PostLoad
    public void onPostLoad() {
        _embedded.setPreviousStatus((ListenedEntity) SerializationUtils.clone(this));
    }

    @PostPersist
    public void onPostPersist(){
        HibernateListener listener = _embedded.getListener(this.getClass());
        if(listener != null)
            listener.onCreate(this);
    }

    @PostRemove
    public void onPostRemove(){
        HibernateListener listener = _embedded.getListener(this.getClass());
        if(listener != null)
            listener.onDelete(this);

    }

    @PostUpdate
    public void onPostUpdate(){
        HibernateListener listener = _embedded.getListener(this.getClass());
        if(listener != null)
            listener.onUpdate(_embedded.getPreviousStatus(),this, _embedded.getPreviousChange(this));
    }





}
