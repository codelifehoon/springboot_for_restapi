package com.aws.atf.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import  org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class SqsMessageService {


    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;


    public void sendMessage(String queueUrl , String payload){

        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

//        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
//        AmazonSQS sqs = AmazonSQSClientBuilder
//                        .standard()
//                        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
//                        .build();

        SendMessageRequest send_msg_request = new SendMessageRequest()
            .withQueueUrl(queueUrl)
            .withMessageBody(payload)
            .withDelaySeconds(5);
        sqs.sendMessage(send_msg_request);

    }

    public List<Message> reviceMessage(String queueUrl){

        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

//        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
//        AmazonSQS sqs = AmazonSQSClientBuilder
//            .standard()
//            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
//            .build();



        List<Message>  messages =  sqs.receiveMessage(queueUrl).getMessages();

        for (Message m : messages) {
            sqs.deleteMessage(queueUrl, m.getReceiptHandle());
        }

        return messages;


    }

}
