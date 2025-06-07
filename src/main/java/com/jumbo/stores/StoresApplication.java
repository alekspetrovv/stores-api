package com.jumbo.stores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StoresApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoresApplication.class, args);
    }

}
