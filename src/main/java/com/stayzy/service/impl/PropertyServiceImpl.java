package com.stayzy.service.impl;

import com.stayzy.model.Property;
import com.stayzy.repository.PropertyRepository;
import com.stayzy.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
