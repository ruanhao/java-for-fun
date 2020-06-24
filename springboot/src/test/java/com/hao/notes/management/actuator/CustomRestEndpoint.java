package com.hao.notes.management.actuator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@RestControllerEndpoint(id="myrestfeatures")
class CustomRestEndpoint {
    private Map<String, Feature> features = new ConcurrentHashMap<>();

    @GetMapping
    public Map<String, Feature> features() {
        return features;
    }

    @GetMapping(path="/{name}")
    public Feature feature(@PathVariable String name) {
        return features.get(name);
    }

    @PostMapping(path="/{name}")
    public void configureFeature(@PathVariable String name, Feature feature) {
        features.put(name, feature);
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class Feature {
        boolean enabled;
    }

}
