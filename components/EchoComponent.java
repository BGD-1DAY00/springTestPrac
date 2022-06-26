package com.executions.demo.components;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class EchoComponent {
    @JsonProperty
    public Map echo;

    public void EchoComponent(String URI){
        Map<String, String> map = new HashMap<>();

        String requestURL = URI;

        String[] components = requestURL.split("/");
        for (int i = 1; i < components.length; i++) {
            String key = components[i];
            String value = "";
            try {
                value = components[i + 1];
            } catch (ArrayIndexOutOfBoundsException e) {

            }
            map.put(key, value);
            i++;
        }
        this.echo = map;
    }
}
