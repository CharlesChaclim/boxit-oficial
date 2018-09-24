package com.uem.boxit;

import com.uem.boxit.config.property.BoxItApiProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableConfigurationProperties(BoxItApiProperty.class)
public class BoxitApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoxitApplication.class, args);
    }
}
