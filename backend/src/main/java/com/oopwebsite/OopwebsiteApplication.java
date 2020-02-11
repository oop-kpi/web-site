package com.oopwebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class OopwebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(OopwebsiteApplication.class, args);
    }

}
