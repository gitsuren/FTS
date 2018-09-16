package com.suru.fts.actuator.config;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomEndpoint implements Endpoint {

    public String getId() {
        return "customEndpoint";
    }

    public boolean isEnabled() {
        return true;
    }

    public boolean isSensitive() {
        return true;
    }

    public List<String> invoke() {
        // Custom logic to build the output
        List<String> messages = new ArrayList<>();
        messages.add("This is message 1");
        messages.add("This is message 2");
        return messages;
    }

    @Override
    public String id() {
        return null;
    }

    @Override
    public boolean enableByDefault() {
        return false;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
