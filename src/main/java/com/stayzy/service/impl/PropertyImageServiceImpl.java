// PropertyImageServiceImpl.java
package com.stayzy.service.impl;

import com.stayzy.model.PropertyImage;
import com.stayzy.repository.PropertyImageRepository;
import com.stayzy.service.PropertyImageService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import com.stayzy.model.Property;
import com.stayzy.repository.PropertyRepository;


@Service
public class PropertyImageServiceImpl implements PropertyImageService {

    private final PropertyImageRepository propertyImageRepository;
    private final PropertyRepository propertyRepository;

    public PropertyImageServiceImpl(PropertyImageRepository propertyImageRepository,
                                    PropertyRepository propertyRepository) {
        this.propertyImageRepository = propertyImageRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    public PropertyImage uploadPropertyImage(UUID propertyId, MultipartFile file) throws Exception {
        // TODO: S3 integration here . For now, we simulate the upload and return a dummy URL.
        String imageUrl = "https://s3.amazonaws.com/your-bucket/" + file.getOriginalFilename();

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

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

