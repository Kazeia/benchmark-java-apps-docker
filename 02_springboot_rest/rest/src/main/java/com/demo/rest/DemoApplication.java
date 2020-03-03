package com.demo.rest;

import co.elastic.apm.attach.ElasticApmAttacher;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
  public static void main(String[] args) {
    Map<String, String> map = new HashMap<String, String>();
    map.put("service_name", "hello-world4");
    map.put("application_packages", "com.demo.rest");
    map.put("server_url", "http://localhost:8200");
    map.put("logging.log_level", "DEBUG");
    map.put("active", "true");
    map.put("instrument", "true");
    ElasticApmAttacher.attach(map);
    SpringApplication.run(DemoApplication.class, args);
  }
}