package com.stayzy.repository;

import com.stayzy.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, UUID> {
    List<Property> findByCityIgnoreCase(String city);
    List<Property> findByPricePerMonthBetween(BigDecimal min, BigDecimal max);
    List<Property> findByPropertyTypeIgnoreCase(String propertyType);

    // Combined filter (optional)
    @Query("SELECT p FROM Property p WHERE " +
           "(:city IS NULL OR p.city = :city) AND " +
           "(:type IS NULL OR p.propertyType = :type) AND " +
           "(:min IS NULL OR p.pricePerMonth >= :min) AND " +
           "(:max IS NULL OR p.pricePerMonth <= :max)")
    List<Property> searchProperties(
        @Param("city") String city,
        @Param("type") String propertyType,
        @Param("min") BigDecimal min,
        @Param("max") BigDecimal max
    );
}
