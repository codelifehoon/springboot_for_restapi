package com.aws.atf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CheckController {

    @GetMapping("/v1/check")
    public String getCheck(){
        log.debug("##### call getCheck");
        System.out.println("##### call getCheck");
        return "OK";
    }
}