package com.stayzy.service;

import com.stayzy.model.Property;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PropertyService {
    Property saveProperty(Property property);
    Optional<Property> getPropertyById(UUID id);
    List<Property> getAllProperties();
    void deleteProperty(UUID id);
    List<Property> findByCity(String city);
    List<Property> findByPriceRange(BigDecimal min, BigDecimal max);
    List<Property> findByPropertyType(String propertyType);
    List<Property> searchProperties(String city, String propertyType, BigDecimal min, BigDecimal max);
}
