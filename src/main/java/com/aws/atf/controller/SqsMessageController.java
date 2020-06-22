package com.aws.atf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.sqs.model.Message;
import com.aws.atf.domain.service.SqsMessageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class SqsMessageController {
    @Autowired
    SqsMessageService sqsMessageService;

    @PostMapping("/v1/sendSqsMessage")
    @ResponseBody
    public String sendSqsMessage(String queueUrl , String payload){

        sqsMessageService.sendMessage(queueUrl,payload);
        return payload;
    }

    @PostMapping("/v1/reciveSqsMessage")
    @ResponseBody
    public List<Message> reciveSqsMessage(String queueUrl ){

        return sqsMessageService.reviceMessage(queueUrl);

    }


}
