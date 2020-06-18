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

//    @Test
//    void sendMessage() {
//        String payload ="payload msg";
//        String queueUrl ="https://sqs.ap-northeast-2.amazonaws.com/120139792823/dev-atf-order-sqs";
//        sqsMessageService.sendMessage(queueUrl,payload);
//    }
}