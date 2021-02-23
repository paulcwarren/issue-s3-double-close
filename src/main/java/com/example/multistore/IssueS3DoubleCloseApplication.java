package com.example.multistore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@SpringBootApplication
public class IssueS3DoubleCloseApplication {

	public static void main(String[] args) {
		SpringApplication.run(IssueS3DoubleCloseApplication.class, args);
	}

	   @Configuration
	    public static class S3StorageConfig {

	        @Value("#{environment.AWS_ACCESS_KEY_ID}")
	        private String accessKey;

	        @Value("#{environment.AWS_SECRET_KEY}")
	        private String secretKey;

	        @Value("#{environment.AWS_REGION}")
	        private String region;

	        @Value("${aws.bucket:#{environment.AWS_BUCKET}}")
	        private String bucket;

	        @Bean
	        public AmazonS3 client() {
	            return AmazonS3ClientBuilder.standard()
	                        .withCredentials(new AWSStaticCredentialsProvider(
	                                            new BasicAWSCredentials(accessKey,secretKey)))
	                        .withRegion(Regions.fromName(region))
	                        .build();
	        }
	    }
}
