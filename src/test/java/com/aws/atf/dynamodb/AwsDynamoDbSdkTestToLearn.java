package com.aws.atf.dynamodb;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.aws.atf.AtfApplication;

import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AtfApplication.class)
public class AwsDynamoDbSdkTestToLearn {



    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    private AmazonDynamoDB amazonDynamoDb;
    private Map<String, AttributeValue> item;


    @BeforeEach
    void setUp() {


        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey,secretKey);
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
//        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "ap-northeast-2");



        amazonDynamoDb = AmazonDynamoDBClientBuilder.standard()
                                                   .withCredentials(awsCredentialsProvider) // (3)
                                                   .withRegion(Regions.AP_NORTHEAST_2)
                                                   .build();

        item = new HashMap<>();
        item.put("id", (new AttributeValue()).withS("uuid"));
        item.put("mentionId", (new AttributeValue()).withN("1"));
        item.put("content", (new AttributeValue()).withS("comment content"));
        item.put("deleted", (new AttributeValue()).withBOOL(false));
        item.put("createdAt", (new AttributeValue()).withS("to be changed"));
        item.put("deletedAt", (new AttributeValue()).withS("to be changed"));

    }




    @Test
    public void emptyTest(){

        System.out.println("####emptyTest");
    }

    @Test
    void putItem_dev_atf_temp_music_TableCreation_StatusOk() {
        Map<String, AttributeValue> item = new HashMap<>();

        item.put("Artist", (new AttributeValue()).withS("art1"));
        item.put("songTitle", (new AttributeValue()).withS("song2"));

        PutItemRequest putItemRequest = (new PutItemRequest())
            .withTableName("dev-atf-temp-music")
            .withItem(item);

        PutItemResult putItemResult = amazonDynamoDb.putItem(putItemRequest);
        then(putItemResult.getSdkHttpMetadata().getHttpStatusCode()).isEqualTo(200);
    }

    @Test
    void getItem_dev_atf_temp_music_FoundItem() {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("Artist", (new AttributeValue()).withS("art1"));



        GetItemRequest getItemRequest = (new GetItemRequest())
                                        .withTableName("dev-atf-temp-music")
                                        .withKey(key);

        GetItemResult getItemResult = amazonDynamoDb.getItem(getItemRequest);

        System.out.println("getItem_ShouldBeCalledAfterPuttingItem_FoundItem" +getItemResult.toString());
    }

    @Test
    void deleteItem_dev_atf_temp_music__StatsOk() {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("Artist", (new AttributeValue()).withS("art1"));

        DeleteItemRequest deleteItemRequest = (new DeleteItemRequest())
                                                .withTableName("dev-atf-temp-music")
                                                .withKey(key);

        DeleteItemResult deleteItemResult = amazonDynamoDb.deleteItem(deleteItemRequest);
        then(deleteItemResult.getSdkHttpMetadata().getHttpStatusCode()).isEqualTo(200);
    }


    @Test
    void createTable_ValidInput_TableHasBeenCreated() {
        CreateTableRequest createTableRequest = (new CreateTableRequest())
            .withAttributeDefinitions(
                new AttributeDefinition("id", ScalarAttributeType.S),
                new AttributeDefinition("mentionId", ScalarAttributeType.N),
                new AttributeDefinition("createdAt", ScalarAttributeType.S)
            ).withTableName("Comment").withKeySchema(
                new KeySchemaElement("id", KeyType.HASH)
            ).withGlobalSecondaryIndexes(
                (new GlobalSecondaryIndex())
                    .withIndexName("byMentionId")
                    .withKeySchema(
                        new KeySchemaElement("mentionId", KeyType.HASH),
                        new KeySchemaElement("createdAt", KeyType.RANGE))
                    .withProjection(
                        (new Projection()).withProjectionType(ProjectionType.ALL))
                    .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L))
            ).withProvisionedThroughput(
                new ProvisionedThroughput(1L, 1L)
            );

        boolean hasTableBeenCreated = TableUtils.createTableIfNotExists(amazonDynamoDb, createTableRequest);
        then(hasTableBeenCreated).isTrue();
    }

    @Test
    void putItem_ShouldBeCalledAfterTableCreation_StatusOk() {
        Map<String, AttributeValue> item = new HashMap<>();

        item.put("id", (new AttributeValue()).withS("uuid"));
        item.put("mentionId", (new AttributeValue()).withN("1"));
        item.put("content", (new AttributeValue()).withS("comment content"));
        item.put("deleted", (new AttributeValue()).withBOOL(false));
        item.put("createdAt", (new AttributeValue()).withS("1836-03-07T02:21:30.536Z"));

        PutItemRequest putItemRequest = (new PutItemRequest())
            .withTableName("Comment")
            .withItem(item);

        PutItemResult putItemResult = amazonDynamoDb.putItem(putItemRequest);
        then(putItemResult.getSdkHttpMetadata().getHttpStatusCode()).isEqualTo(200);
    }


    @Test
    void getItem_ShouldBeCalledAfterPuttingItem_FoundItem() {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("id", (new AttributeValue()).withS("uuid"));

        GetItemRequest getItemRequest = (new GetItemRequest())
            .withTableName("Comment")
            .withKey(key);

        GetItemResult getItemResult = amazonDynamoDb.getItem(getItemRequest);

        System.out.println(getItemResult.toString());

//        then(getItemResult.getItem()).containsAllEntriesOf(item);
    }

}
