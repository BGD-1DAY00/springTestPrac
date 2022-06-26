package com.executions.demo.components;

import com.fasterxml.jackson.annotation.JsonProperty;


import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class HeadersComponent {
    @JsonProperty("headers")
    public Map headers;


    public HeadersComponent(Enumeration<String> headers, HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        while (headers.hasMoreElements()) {
            String header_name = headers.nextElement();
            String header_name_lowercase = header_name.toLowerCase();
            if (header_name_lowercase.startsWith("x-zoo")) {
                continue;
            } else if (header_name_lowercase.startsWith("x-google")) {
                continue;
            } else if (header_name_lowercase.startsWith("x-appengine")) {
                continue;
            }
            map.put(header_name,  request.getHeader(header_name));
        }
        this.headers = map;
    }
}

