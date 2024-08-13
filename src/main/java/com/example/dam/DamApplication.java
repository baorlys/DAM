package com.example.dam;

import com.example.dam.config.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableJpaAuditing
@CrossOrigin
@EnableConfigurationProperties(StorageProperties.class)
public class DamApplication {

    public static void main(String[] args) {
        SpringApplication.run(DamApplication.class, args);
    }

}
