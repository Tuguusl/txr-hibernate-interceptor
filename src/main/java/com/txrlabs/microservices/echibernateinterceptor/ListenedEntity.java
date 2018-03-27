package com.txrlabs.microservices.echibernateinterceptor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.SerializationUtils;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class ListenedEntity implements Serializable{

    @Transient
    @JsonIgnore
    private ListenedEntity _previousStatus;

    @Transient
    @JsonIgnore
    private EcListener _listener;

    @Transient
    @JsonIgnore
    private EcListener getListener(){

        if(_listener != null)
            return _listener;

        WithListener notation = this.getClass().getAnnotation(WithListener.class);
        if(notation == null)
            return null;
        else {
            _listener = ContextProvider.getBean(notation.value());
            return _listener;
        }
    }

    @PostLoad
    public void onPostLoad() {
        this._previousStatus = (ListenedEntity) SerializationUtils.clone(this);
    }

    @PostPersist
    public void onPostPersist(){
        EcListener listener = getListener();
        if(listener != null)
            listener.onCreate(this);
    }

    @PostRemove
    public void onPostRemove(){
        EcListener listener = getListener();
        if(listener != null)
            listener.onDelete(this);

    }

    @PostUpdate
    public void onPostUpdate(){
        EcListener listener = getListener();
        if(listener != null)
            listener.onUpdate(this._previousStatus,this);
    }





}
