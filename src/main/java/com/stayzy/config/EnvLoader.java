package com.stayzy.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvLoader {

    static {
        Dotenv dotenv = Dotenv.configure()
                .directory("./")
                .ignoreIfMissing()
                .load();

        String url = dotenv.get("SPRING_DATASOURCE_URL");
        String user = dotenv.get("SPRING_DATASOURCE_USERNAME");
        String pass = dotenv.get("SPRING_DATASOURCE_PASSWORD");

        // Cloudflare R2 properties
        String r2AccountId = dotenv.get("R2_ACCOUNT_ID");
        String r2AccessKey = dotenv.get("R2_ACCESS_KEY");
        String r2SecretKey = dotenv.get("R2_SECRET_KEY");
        String r2BucketName = dotenv.get("R2_BUCKET_NAME");
        String r2CustomDomain = dotenv.get("R2_CUSTOM_DOMAIN");

        if (url != null) System.setProperty("spring.datasource.url", url);
        if (user != null) System.setProperty("spring.datasource.username", user);
        if (pass != null) System.setProperty("spring.datasource.password", pass);

         // Set Cloudflare R2 properties
        if (r2AccountId != null) System.setProperty("cloudflare.r2.accountId", r2AccountId);
        if (r2AccessKey != null) System.setProperty("cloudflare.r2.accessKey", r2AccessKey);
        if (r2SecretKey != null) System.setProperty("cloudflare.r2.secretKey", r2SecretKey);
        if (r2BucketName != null) System.setProperty("cloudflare.r2.bucketName", r2BucketName);
        if (r2CustomDomain != null) System.setProperty("cloudflare.r2.customDomain", r2CustomDomain);
    }
}
