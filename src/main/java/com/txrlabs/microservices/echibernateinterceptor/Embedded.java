package com.txrlabs.microservices.echibernateinterceptor;

import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Embedded implements Serializable{

    private ListenedEntity previousStatus;
    private HibernateListener listener;
    private Gson gson = new Gson();


    ListenedEntity getPreviousStatus() {
        return previousStatus;
    }

    void setPreviousStatus(ListenedEntity previousStatus) {
        this.previousStatus = previousStatus;
    }


    HibernateListener getListener(Class <? extends ListenedEntity> clazz){

        if(listener != null)
            return listener;

        WithListener notation = clazz.getAnnotation(WithListener.class);
        if(notation == null || !ContextProvider.validContext())
            return null;
        else {
            listener = ContextProvider.getBean(notation.value());
            return listener;
        }
    }



    List<String> getPreviousChange(ListenedEntity entity){

        Type TYPE = new TypeToken<Map<String, Object>>(){}.getType();
        Set<String> differences = new HashSet<>();

        Map<String, Object> _old = gson.fromJson(gson.toJsonTree(previousStatus),TYPE);
        Map<String, Object> _new = gson.fromJson(gson.toJsonTree(entity),TYPE);

        MapDifference<String, Object> difference = Maps.difference(_new,_old);

        differences.addAll(difference.entriesOnlyOnLeft().keySet());
        differences.addAll(difference.entriesOnlyOnRight().keySet());
        differences.addAll(difference.entriesDiffering().keySet());


        return Lists.newArrayList(differences);
    }

}
