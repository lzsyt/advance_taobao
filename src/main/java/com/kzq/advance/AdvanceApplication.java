package com.kzq.advance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AdvanceApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AdvanceApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AdvanceApplication.class, args);
    }
/*
    public static void main(String[] args) {
        SpringApplication.run(AdvanceApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AdvanceApplication.class);
    }*/

}