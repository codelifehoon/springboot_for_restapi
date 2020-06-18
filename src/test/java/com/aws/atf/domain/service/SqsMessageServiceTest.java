package com.aws.atf.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.aws.atf.AtfApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AtfApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class SqsMessageServiceTest {

    @Autowired
    SqsMessageService sqsMessageService;

    @Test
    void sendMessage() {
//        sqsMessageService.sendMessage(payload);
    }
}