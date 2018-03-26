package com.txrlabs.microservices.echibernateinterceptor;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(value = "interceptor")
public class ListenersConfiguration {

    private Map<String,Map<String,String>> listeners = new HashMap<>();

    public Map<String, Map<String, String>> getListeners() {
        return listeners;
    }

    public void setListeners(Map<String, Map<String, String>> listeners) {
        this.listeners = listeners;
    }

    public Map<Class, InterceptorListener> getAllListeners() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        Map<Class, InterceptorListener> val = new HashMap<>();

        for(Map.Entry<String, Map<String, String>> entry : listeners.entrySet())
        {
            val.put(
                    Class.forName(entry.getValue().get("class")),
                    (InterceptorListener) Class.forName(entry.getValue().get("listener")).newInstance()
            );
        }

        return val;
    }
}
