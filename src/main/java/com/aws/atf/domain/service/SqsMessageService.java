package com.aws.atf.domain.service;

import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class SqsMessageService {


    public void sendMessage(String queueUrl , String payload){
        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

        SendMessageRequest send_msg_request = new SendMessageRequest()
            .withQueueUrl(queueUrl)
            .withMessageBody(payload)
            .withDelaySeconds(5);
        sqs.sendMessage(send_msg_request);


    }

}
