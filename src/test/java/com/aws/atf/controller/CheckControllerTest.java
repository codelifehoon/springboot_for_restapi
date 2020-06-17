package com.aws.atf.controller;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.aws.atf.AtfApplication;
import com.aws.atf.domain.order.payload.Order;
import com.aws.atf.domain.order.payload.OrderDetail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AtfApplication.class , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CheckControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void getCheck() {

        System.out.println("#####");
    }

    @Test
    public void requestOrder() {

        String requestUrl = "/v1/requestOrder";

        ArrayList<OrderDetail> orderDetail = new ArrayList<>();
        orderDetail.add(OrderDetail.builder().prdNo("P001").prdPrice("1000").sellerNm("A-SELLER").build());
        orderDetail.add(OrderDetail.builder().prdNo("P002").prdPrice("2000").sellerNm("B-SELLER").build());


        Order payload = Order.builder().ordNo("11111")
                             .ordDt(new Date())
                             .orderDetail(orderDetail)
                             .build();

        String resultBody = testRestTemplate.exchange(requestUrl, HttpMethod.POST
            , new HttpEntity(payload, null)
            , String.class).getBody();

        assertThat(resultBody).isNotEmpty();
        System.out.println("########################################");
        System.out.println(resultBody);


    }

}