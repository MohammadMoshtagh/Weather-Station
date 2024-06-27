package edu.sharif;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.TimeZone;

@SpringBootApplication
@EnableCaching
public class AuthenticationManagerApplication {

    @Value("${application.timezone:UTC}")
    private String applicationTimeZone;

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationManagerApplication.class, args);
    }

    /**
     * Initializes some configs for spring boot application.
     */
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone(applicationTimeZone));
    }
}
