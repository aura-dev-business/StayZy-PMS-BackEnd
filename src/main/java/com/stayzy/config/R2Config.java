
package com.stayzy.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import java.net.URI;


@Configuration
public class R2Config {

    @Value("${cloudflare.r2.accountId}")
    private String accountId;

    @Value("${cloudflare.r2.accessKey}")
    private String accessKey;

    @Value("${cloudflare.r2.secretKey}")
    private String secretKey;

    @Value("${cloudflare.r2.bucketName}")
    private String bucketName;

    @Bean
    public S3Client r2Client() {
        String endpoint = "https://" + accountId + ".r2.cloudflarestorage.com";
        
        // Add debug logging
        System.out.println("R2 Configuration:");
        System.out.println("Account ID: " + accountId);
        System.out.println("Access Key: " + accessKey.substring(0, 5) + "..."); // Show first 5 chars for security
        System.out.println("Bucket Name: " + bucketName);
        System.out.println("Endpoint: " + endpoint);
        
        return S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.of("auto"))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
                .build();
    }
}
