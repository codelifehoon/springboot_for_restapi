package com.aws.atf.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aws.atf.domain.order.payload.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class OrderController {

    @PostMapping("/v1/requestOrder")
    @ResponseBody
    public Order requestOrder(@RequestBody Order order){

        return order;
    }
}
