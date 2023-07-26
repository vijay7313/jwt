package com.jwt.configure;

import org.springframework.beans.factory.annotation.Value;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.jwt.dao")
public class DynamoDBConfiguration {

	@Value("dynamodb.ap-south-1.amazonaws.com")
	String endpoint;
	@Value("ap-south-1")
	String region;
	@Value("AKIASXRNKFU45JUZSR7E")
	String accesskey;
	@Value("P7JF6JnUUf5o++xHECGyokmCQp4nD43kUlhdg4C3")
	String secretkey;

	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
		return AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accesskey, secretkey)))
				.build();
	}

}
