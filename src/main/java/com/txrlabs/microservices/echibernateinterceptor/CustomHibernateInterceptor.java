package com.txrlabs.microservices.echibernateinterceptor;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Map;

@Component
public class CustomHibernateInterceptor extends EmptyInterceptor {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    ListenersConfiguration listenersConfiguration;

    private Map<Class, InterceptorListener> listeners;

    @PostConstruct
    public void initialize() throws IllegalAccessException, InstantiationException, ClassNotFoundException {

        log.info("initializing interceptor");
        listeners = listenersConfiguration.getAllListeners();
        log.info("intercepting for "+ listeners.size()+ " listeners");
    }

    private InterceptorListener getListener(Object obj){

        return listeners
                .entrySet()
                .stream()
                .filter(entry->entry.getKey().isInstance(obj))
                .map(Map.Entry::getValue)
                .findAny()
                .orElse(null);
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {

        try {

            InterceptorListener listener = getListener(entity);
            if (listener != null)
                listener.onChange(previousState, currentState, propertyNames, types, entity);

        }catch (Exception ex){
            log.error("Failed notifying onChange listener");
        }

        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {

        try {

            InterceptorListener listener = getListener(entity);
            if (listener != null)
                listener.onDelete(state, propertyNames, types, entity);

        }catch (Exception ex){
            log.error("Failed notifying onDelete listener");
        }
        super.onDelete(entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {

        try {

            InterceptorListener listener = getListener(entity);
            if (listener != null)
                listener.onCreate(state, propertyNames, types, entity);

        }catch (Exception ex){
            log.error("Failed notifying onCreate listener");
        }
        return super.onSave(entity, id, state, propertyNames, types);

    }
}