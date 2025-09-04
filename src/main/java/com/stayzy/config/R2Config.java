
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

	@Bean
	public S3Client r2Client() {
		if (accountId == null || accountId.isEmpty() || accountId.equals("YOUR_ACCOUNT_ID")) {
			throw new IllegalArgumentException("Cloudflare R2 accountId is missing or not set. Please set cloudflare.r2.accountId in your application.properties or environment.");
		}
		String endpoint = "https://" + accountId + ".r2.cloudflarestorage.com/stayz-bucket" + //
                        "";
		return S3Client.builder()
				.endpointOverride(URI.create(endpoint))
				.region(Region.of("auto")) // R2 uses "auto"
				.credentialsProvider(StaticCredentialsProvider.create(
						AwsBasicCredentials.create(accessKey, secretKey)
				))
				.serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
				.build();
	}
}