package com.bikkadit.electoronic.store.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayDeque;
import java.util.ArrayList;


@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(getApiInfo());
        return docket;
    }

    private ApiInfo getApiInfo() {

        ApiInfo apiInfo = new ApiInfo(
                "Electronic Stores Backend : APIS",
                "This Is Backend Project Created By Sujit",
                "1.0v",
                "www.SujitPatil.com",
                new Contact("Sujit", "https/www.instagram.com/itz_sujitt_96k", "patilSujit205@Gmail.com"),

                "License of APIS",
                "https://www.SujitPatil.com/about",
                new ArrayDeque<>()
        );

        return apiInfo;
    }


}


