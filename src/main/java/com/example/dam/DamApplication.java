package com.example.dam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing

public class DamApplication {

    public static void main(String[] args) {
        SpringApplication.run(DamApplication.class, args);
    }

}
