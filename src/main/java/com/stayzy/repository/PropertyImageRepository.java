package com.stayzy.repository;

import com.stayzy.model.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PropertyImageRepository extends JpaRepository<PropertyImage, UUID> {
    // Optional custom query method to fetch images by property
    List<PropertyImage> findByPropertyId(UUID propertyId);
}
