// PropertyImageService.java
package com.stayzy.service;

import com.stayzy.model.PropertyImage;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;


public interface PropertyImageService {
    PropertyImage uploadPropertyImage(UUID propertyId, MultipartFile file) throws Exception;
    List<PropertyImage> getPropertyImages(UUID propertyId);
}

