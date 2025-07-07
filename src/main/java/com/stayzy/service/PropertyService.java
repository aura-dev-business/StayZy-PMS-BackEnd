package com.stayzy.service;

import com.stayzy.model.Property;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PropertyService {
    Property saveProperty(Property property);
    Optional<Property> getPropertyById(UUID id);
    List<Property> getAllProperties();
    void deleteProperty(UUID id);
    // Add more methods as needed (e.g., search by city, price, etc.)
}
