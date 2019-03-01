package com.dylankilbride.grouppay.Configuration;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfiguration {

	@Value("${aws.access.key.id}")
	private String accessKey;

	@Value("${aws.access.key.secret}")
	private String secretKey;

	@Value("${aws.region}")
	private String region;

	@Bean
	public BasicAWSCredentials basicAWSCredentials() {
		return new BasicAWSCredentials(accessKey, secretKey);
	}

	@Bean
	public AmazonS3 amazonS3Client(AWSCredentials awsCredentials) {
		AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard();
		ClientConfiguration awsClientConfig = new ClientConfiguration();
		awsClientConfig.setProtocol(Protocol.HTTP);
		builder.withCredentials(new AWSStaticCredentialsProvider(awsCredentials));
		builder.setRegion(region);
		builder.setClientConfiguration(new ClientConfiguration(awsClientConfig));
		AmazonS3 amazonS3 = builder.build();
		return amazonS3;
	}
}