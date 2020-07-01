package com.aws.atf.domain.service;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.amazonaws.services.sqs.model.Message;
import com.aws.atf.AtfApplication;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AtfApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SqsMessageServiceTest {

    @Autowired
    SqsMessageService sqsMessageService;


    @Test
    void reviceMessage() {

        String payload ="payload msg";

        String queueUrl ="https://sqs.ap-northeast-2.amazonaws.com/120139792823/dev-atf-order-sqs";
        List<Message> messages = sqsMessageService.reviceMessage(queueUrl);
        assertThat(messages).isNotNull();
    }

    @Test
    void sendMessage() {
        String payload ="local_PayloadMsg : " + (new Date()).toString();
        String queueUrl ="https://sqs.ap-northeast-2.amazonaws.com/120139792823/dev-atf-order-sqs";
        sqsMessageService.sendMessage(queueUrl,payload);
    }

    @Test
    void testCode() {
        String payload ="payload msg : " + (new Date()).toString();
        System.out.println(payload);

    }

}