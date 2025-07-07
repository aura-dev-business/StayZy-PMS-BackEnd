package com.stayzy.repository;

import com.stayzy.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, UUID> {
    // Add custom query methods if needed
}
