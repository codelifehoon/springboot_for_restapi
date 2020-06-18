package com.aws.atf.config;

import  org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
//@Profile(value = {"local", "dev"})
@Configuration
@EnableSwagger2
public class SwaggerConfig {

//    @Value("${spring.profiles.active}")
//    private String springProfilesActive;

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2).select()
                                                      .apis(RequestHandlerSelectors.basePackage("com.aws.atf.controller"))
                                                      .paths(PathSelectors.any())
                                                      .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ATF with Swagger")
                .description("ATF with Swagger description")
//                .termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
//                .license("Apache License Version 2.0")
//                .licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
                .build();
    }

}