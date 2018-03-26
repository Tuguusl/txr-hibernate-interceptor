package com.txrlabs.microservices.echibernateinterceptor;

import org.aspectj.apache.bcel.generic.RET;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CustomHibernateInterceptor extends EmptyInterceptor implements BeanFactoryAware {

    private Logger log = LoggerFactory.getLogger(getClass());


    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }


    private InterceptorListener getListener(Object entity){
        HibernateListener notation = entity.getClass().getAnnotation(HibernateListener.class);
        if(notation == null)
            return null;
        else
            return  beanFactory.getBean(entity.getClass().getAnnotation(HibernateListener.class).value());
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {

        if(super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types)) {

            InterceptorListener listener = getListener(entity);
            if (listener != null)
                listener.onChange(previousState, currentState, propertyNames, types, entity);
            return true;
        }

        return false;
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {

        super.onDelete(entity, id, state, propertyNames, types);

        InterceptorListener listener = getListener(entity);
        if (listener != null)
            listener.onDelete(state, propertyNames, types, entity);
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {

        if(super.onSave(entity, id, state, propertyNames, types)) {

            InterceptorListener listener = getListener(entity);
            if (listener != null)
                listener.onCreate(state, propertyNames, types, entity);
            return true;
        }

        return false;
    }
}