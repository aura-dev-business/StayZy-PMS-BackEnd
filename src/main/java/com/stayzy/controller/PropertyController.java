package com.stayzy.controller;

import com.stayzy.model.Property;
import com.stayzy.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    // ------------------ Create ------------------

    @PostMapping
    public ResponseEntity<List<Property>> createProperties(@RequestBody List<Property> properties) {
        List<Property> saved = properties.stream()
            .map(propertyService::saveProperty)
            .toList();
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/city/{city}")
    public ResponseEntity<Property> createPropertyWithCity(
        @PathVariable String city,
        @RequestBody Property property
    ) {
        property.setCity(city); // Override city if necessary
        Property saved = propertyService.saveProperty(property);
        return ResponseEntity.ok(saved);
    }

    // ------------------ Read ------------------

    @GetMapping
    public List<Property> getAllProperties() {
        return propertyService.getAllProperties();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable UUID id) {
        Optional<Property> property = propertyService.getPropertyById(id);
        return property.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable UUID id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }

    // ------------------ Search ------------------

    @GetMapping("/search")
    public List<Property> searchProperties(
        @RequestParam(required = false) String city,
        @RequestParam(required = false) String propertyType,
        @RequestParam(required = false) BigDecimal minPrice,
        @RequestParam(required = false) BigDecimal maxPrice
    ) {
        return propertyService.searchProperties(city, propertyType, minPrice, maxPrice);
    }

    // ------------------ Filter Lists for UI ------------------

    @GetMapping("/city/all")
    public List<String> getAllCities() {
        List<String> cities = propertyService.getAllProperties().stream()
                .map(Property::getCity)
                .filter(city -> city != null && !city.isBlank())
                .distinct()
                .collect(Collectors.toList());

        System.out.println("Fetched cities: " + cities); // Debug
        return cities;
    }

    @GetMapping("/type/all")
    public List<String> getAllPropertyTypes() {
        return propertyService.getAllProperties().stream()
                .map(Property::getPropertyType)
                .filter(type -> type != null && !type.isBlank())
                .distinct()
                .collect(Collectors.toList());
    }

    @GetMapping("/price/all")
    public List<String> getAllPriceRanges() {
        List<BigDecimal> prices = propertyService.getAllProperties().stream()
                .map(Property::getPricePerMonth)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Set<String> priceRanges = new HashSet<>();

        for (BigDecimal price : prices) {
            if (price.compareTo(new BigDecimal("0")) >= 0 && price.compareTo(new BigDecimal("5000")) <= 0) {
                priceRanges.add("₹0 - ₹5000");
            } else if (price.compareTo(new BigDecimal("5000")) > 0 && price.compareTo(new BigDecimal("10000")) <= 0) {
                priceRanges.add("₹5000 - ₹10000");
            } else if (price.compareTo(new BigDecimal("10000")) > 0 && price.compareTo(new BigDecimal("20000")) <= 0) {
                priceRanges.add("₹10000 - ₹20000");
            } else if (price.compareTo(new BigDecimal("20000")) > 0 && price.compareTo(new BigDecimal("50000")) <= 0) {
                priceRanges.add("₹20000 - ₹50000");
            } else if (price.compareTo(new BigDecimal("50000")) > 0) {
                priceRanges.add("₹50000+");
            }
        }

        List<String> sortedRanges = new ArrayList<>(priceRanges);

        List<String> definedOrder = List.of(
                "₹0 - ₹5000",
                "₹5000 - ₹10000",
                "₹10000 - ₹20000",
                "₹20000 - ₹50000",
                "₹50000+"
        );

        return definedOrder.stream()
                .filter(sortedRanges::contains)
                .collect(Collectors.toList());
    }

    // ------------------ Filter by Attributes ------------------

    @GetMapping("/city/{city}")
    public List<Property> getByCity(@PathVariable String city) {
        return propertyService.findByCity(city);
    }

    @GetMapping("/type/{type}")
    public List<Property> getByType(@PathVariable String type) {
        return propertyService.findByPropertyType(type);
    }

    @GetMapping("/price")
    public List<Property> getByPriceRange(
        @RequestParam BigDecimal min,
        @RequestParam BigDecimal max
    ) {
        return propertyService.findByPriceRange(min, max);
    }
}
