package edu.neu.csye6225.config;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AWSS3Config {
//    // Access key id will be read from the application.properties file during the application intialization.
//    @Value("${aws.access_key_id}")
//    private String accessKeyId = System.getenv("AWS_ACCESS_KEY");
//    // Secret access key will be read from the application.properties file during the application intialization.
//    @Value("${aws.secret_access_key}")
//    private String secretAccessKey = System.getenv("AWS_SECRET_KEY");
//    // Region will be read from the application.properties file  during the application intialization.
    @Value("${aws.s3.region}")
    private String region = System.getenv("AWS_REGION");

    @Bean
    public AmazonS3 getAmazonS3Cient() {
        //final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        // Get AmazonS3 client and return the s3Client object.
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new InstanceProfileCredentialsProvider(false))
                .build();
    }

    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new InstanceProfileCredentialsProvider(false))
                .withRegion(Regions.US_EAST_1)
                .build();
        return new DynamoDBMapper(client, DynamoDBMapperConfig.DEFAULT);
    }
}
