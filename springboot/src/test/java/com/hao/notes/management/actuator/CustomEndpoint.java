package com.hao.notes.management.actuator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id="myfeatures")
class CustomEndpoint {
    private Map<String, Boolean> features = new ConcurrentHashMap<>();

    @ReadOperation // map to HTTP GET
    public Map<String, Boolean> features() {
        return features;
    }

    @ReadOperation // map to HTTP GET
    public boolean feature(@Selector String name) {
        return features.get(name);
    }

    @WriteOperation // map to HTTP POST
    /*
     * Endpoint operations don't support complex input
     */
    public void configureFeature(@Selector String name, boolean enabled) {
        features.put(name, enabled);
    }

    @DeleteOperation // map to HTTP DELETE
    public void deleteFeature(@Selector String name) {
        features.remove(name);
    }


}
