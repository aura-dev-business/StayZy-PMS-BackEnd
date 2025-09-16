// PropertyImageServiceImpl.java
package com.stayzy.service.impl;

import com.stayzy.model.PropertyImage;
import com.stayzy.repository.PropertyImageRepository;
import com.stayzy.service.PropertyImageService;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import software.amazon.awssdk.core.sync.RequestBody;


import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.web.multipart.MultipartFile;
import com.stayzy.model.Property;
import com.stayzy.repository.PropertyRepository;

@Service
public class PropertyImageServiceImpl implements PropertyImageService {

    private final PropertyImageRepository propertyImageRepository;
    private final PropertyRepository propertyRepository;
    private final S3Client r2Client;
    private final String bucketName;
    private final String customDomain; // Use custom domain instead of accountId

    public PropertyImageServiceImpl(
            PropertyImageRepository propertyImageRepository,
            PropertyRepository propertyRepository,
            S3Client r2Client,
            @Value("${cloudflare.r2.bucketName}") String bucketName,
            @Value("${cloudflare.r2.customDomain}") String customDomain) { // Add custom domain property
        this.propertyImageRepository = propertyImageRepository;
        this.propertyRepository = propertyRepository;
        this.r2Client = r2Client;
        this.bucketName = bucketName;
        this.customDomain = customDomain;

        new Thread(() -> {
            try {
                Thread.sleep(5000); // Wait a bit for app to start
                verifyBucketExists();
            } catch (Exception e) {
                System.err.println("⚠️  Bucket verification failed (non-critical): " + e.getMessage());
            }
        }).start();
    }

     private void verifyBucketExists() {
        try {
            // Try to list objects in the bucket (or use headBucket)
            r2Client.listObjectsV2(builder -> builder.bucket(bucketName).maxKeys(1));
            System.out.println("✅ Bucket '" + bucketName + "' exists and is accessible");
        } catch (Exception e) {
            System.err.println("❌ Bucket '" + bucketName + "' does not exist or is not accessible:");
            e.printStackTrace();
            throw new RuntimeException("Bucket verification failed: " + e.getMessage(), e);
        }
    }

    @Override
    public PropertyImage uploadPropertyImage(UUID propertyId, MultipartFile file) throws Exception {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        String key = UUID.randomUUID() + "-" +
                    URLEncoder.encode(file.getOriginalFilename(), StandardCharsets.UTF_8);

        try (InputStream inputStream = file.getInputStream()) {
            r2Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromInputStream(inputStream, file.getSize())
            );
        }

        // If you are using a custom domain pointing to the bucket:
        String imageUrl = "https://" + customDomain + "/" + key;

        PropertyImage propertyImage = new PropertyImage();
        propertyImage.setProperty(property);
        propertyImage.setImageUrl(imageUrl);

        return propertyImageRepository.save(propertyImage);
    }

    @Override
    public List<PropertyImage> getPropertyImages(UUID propertyId) {
        return propertyImageRepository.findByPropertyId(propertyId);
    }
}
