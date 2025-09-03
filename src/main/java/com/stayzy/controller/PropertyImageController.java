// PropertyImageController.java
package com.stayzy.controller;

import com.stayzy.model.PropertyImage;
import com.stayzy.service.PropertyImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/properties/{propertyId}/images")
public class PropertyImageController {

    private final PropertyImageService propertyImageService;

    public PropertyImageController(PropertyImageService propertyImageService) {
        this.propertyImageService = propertyImageService;
    }

    // ✅ Upload a property image
    @PostMapping("/upload")
    public ResponseEntity<PropertyImage> uploadPropertyImage(
            @PathVariable UUID propertyId,
            @RequestParam("file") MultipartFile file) {
        try {
            PropertyImage propertyImage = propertyImageService.uploadPropertyImage(propertyId, file);
            return ResponseEntity.ok(propertyImage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ✅ Get all images for a property
    @GetMapping
    public ResponseEntity<List<PropertyImage>> getPropertyImages(@PathVariable UUID propertyId) {
        List<PropertyImage> images = propertyImageService.getPropertyImages(propertyId);
        return ResponseEntity.ok(images);
    }
}
