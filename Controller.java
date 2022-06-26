package com.executions.demo;

import com.executions.demo.components.DateComponent;
import com.executions.demo.components.EchoComponent;
import com.executions.demo.components.HeadersComponent;
import com.executions.demo.components.IPComponent;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.*;
@CrossOrigin
@RestController
public class Controller {
    public HttpServletRequest httpServletRequest;
    public Date date;
    public String string;

    Controller(){
        httpServletRequest = null;
        date = null;

    }

    public void setRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }
    public void setDate(Date date) {
        this.date = date;
    }



    @GetMapping("/")
    String open() {
        return "welcome to the home page";
    }

    @GetMapping("/ip")
    public IPComponent getIp(HttpServletRequest request) {
        if(this.httpServletRequest != null)
            request = this.httpServletRequest;
        return new IPComponent(request.getRemoteAddr());
    }

    @GetMapping("/date")
    DateComponent findDate(Date date) {
        if(this.date != null)
            return new DateComponent(this.date);
        return new DateComponent(new Date());
    }


    @GetMapping("/headers")
    public HeadersComponent headers(HttpServletRequest request) throws ParseException {
        return new HeadersComponent(request.getHeaderNames(), request);

    }

    @GetMapping("/echo/**")
    public EchoComponent echo (HttpServletRequest request){
        return new EchoComponent(request.getRequestURI().substring(1));
       // Map<String, String> map = new HashMap<>();

//        String requestURL = request.getRequestURI().substring(1);
//
//        String[] components = requestURL.split("/");
//        for (int i = 1; i < components.length; i++) {
//            String key = components[i];
//            String value = "";
//            try {
//                value = components[i + 1];
//            } catch (ArrayIndexOutOfBoundsException e) {
//
//            }
//            map.put(key, value);
//            i++;
//        }
//        return map;
    }

        @GetMapping("/validate/")
        Map<String, String> validate (String json){
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String, String> map = new HashMap<>();
            if (json == null) {
                map.put("Cannot be emptey", "Please enter a key value pair");
                return map;
            }
            if (!Objects.equals(json, "")) {
                map.put("empty", "false");
                try {
                    objectMapper.readTree(json);
                } catch (JacksonException e) {
                    map.put("validate", "false");
                    return map;
                }
                map.put("validate", "true");
                return map;
            }
            map.put("empty", "true");
            map.put("validate", "false");
            return map;
        }

        @GetMapping("/jscode")
        String validatejsocode (HttpServletRequest request) {
            Map<String, String> map = new HashMap<>();
            String requestURL = request.getRemoteAddr();
            return String.format("alert(\"Your IP address is: %1$s\")", requestURL);
        }
        @GetMapping("/mdfive/")
        public Map<String, String> mdfive(@RequestParam(name = "text") String arg, HttpServletRequest request){
            HashMap<String, String> map = new HashMap<>();
            String to_be_md5 = request.getParameter("text");
            String md5 = JSONUtilities.generateMD5(to_be_md5);
            map.put("original", to_be_md5);
            map.put("md5", md5);
            return map;
        }

    @GetMapping("/cookie")
    public Map<String, String> cookieSet(@RequestParam(name = "text") String arg, HttpServletRequest request, HttpServletResponse resp){
        HashMap<String, String> map = new HashMap<>();
        Long ms_since_epoch = (new Date()).getTime();
        javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie("jsontestdotcom", "ms:" + ms_since_epoch.toString());
        cookie.setMaxAge(60 * 60 * 24 * 7);
        resp.addCookie(cookie);
        map.put("cookie_status", "Cookie set with name jsontestdotcom");
        return map;
    }
}


