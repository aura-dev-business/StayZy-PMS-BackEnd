// PropertyImageServiceImpl.java
package com.stayzy.service.impl;

import com.stayzy.model.PropertyImage;
import com.stayzy.repository.PropertyImageRepository;
import com.stayzy.service.PropertyImageService;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import com.stayzy.model.Property;
import com.stayzy.repository.PropertyRepository;

@Service
public class PropertyImageServiceImpl implements PropertyImageService {

    private final PropertyImageRepository propertyImageRepository;
    private final PropertyRepository propertyRepository;
    private final S3Client r2Client;
    private final String bucketName;
    private final String accountId;

    public PropertyImageServiceImpl(PropertyImageRepository propertyImageRepository,
            PropertyRepository propertyRepository,
            S3Client r2Client,
            @Value("${cloudflare.r2.bucketName}") String bucketName,
            @Value("${cloudflare.r2.accountId}") String accountId) {
        this.propertyImageRepository = propertyImageRepository;
        this.propertyRepository = propertyRepository;
        this.r2Client = r2Client;
        this.bucketName = bucketName;
        this.accountId = accountId;
    }

    @Override
    public PropertyImage uploadPropertyImage(UUID propertyId, MultipartFile file) throws Exception {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        String key = UUID.randomUUID() + "-" + file.getOriginalFilename();
        try (InputStream inputStream = file.getInputStream()) {
            r2Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .contentType(file.getContentType())
                            .build(),
                    software.amazon.awssdk.core.sync.RequestBody.fromInputStream(inputStream, file.getSize()));
        }
        // Public URL (if bucket is public)
        String imageUrl = "https://" + bucketName + "." + accountId + ".r2.dev/" + key;
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
