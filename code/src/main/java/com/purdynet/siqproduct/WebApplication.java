package com.purdynet.siqproduct;

import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
        if (System.getProperty("com.google.api.client.sample.bigquery.appengine.dashboard.projectId") == null) {
            System.setProperty("com.google.api.client.sample.bigquery.appengine.dashboard.projectId", "swiftiq-master");
        }
    }

    @GetMapping("/")
    public String hello() {
        return "Hello Spring Boot!";
    }
}