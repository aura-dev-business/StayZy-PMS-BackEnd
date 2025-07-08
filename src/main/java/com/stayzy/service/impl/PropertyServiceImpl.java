package com.stayzy.service.impl;

import com.stayzy.model.Property;
import com.stayzy.repository.PropertyRepository;
import com.stayzy.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;

    @Autowired
    public PropertyServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public Property saveProperty(Property property) {
        return propertyRepository.save(property);
    }

    @Override
    public Optional<Property> getPropertyById(UUID id) {
        return propertyRepository.findById(id);
    }

    @Override
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    @Override
    public void deleteProperty(UUID id) {
        propertyRepository.deleteById(id);
    }

    @Override
    public List<Property> findByCity(String city) {
        return propertyRepository.findByCityIgnoreCase(city);
    }

    @Override
    public List<Property> findByPriceRange(BigDecimal min, BigDecimal max) {
        return propertyRepository.findByPricePerMonthBetween(min, max);
    }

    @Override
    public List<Property> findByPropertyType(String propertyType) {
        return propertyRepository.findByPropertyTypeIgnoreCase(propertyType);
    }

    @Override
    public List<Property> searchProperties(String city, String propertyType, BigDecimal min, BigDecimal max) {
        return propertyRepository.searchProperties(city, propertyType, min, max);
    }
}
