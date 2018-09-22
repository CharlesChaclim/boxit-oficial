package com.uem.boxit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BoxitApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoxitApplication.class, args);
    }
}
