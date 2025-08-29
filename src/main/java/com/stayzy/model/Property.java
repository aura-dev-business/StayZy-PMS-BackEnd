package com.stayzy.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "properties")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String city;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "price_per_month", precision = 10, scale = 2, nullable = false)
    private BigDecimal pricePerMonth;

    @Column(name = "property_type")
    private String propertyType;

    @Column(name = "room_type")
    private String roomType;

    @Column(name = "is_featured")
    private Boolean isFeatured = false;

    @Column(name = "available")
    private Boolean available = true;

    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PropertyImage> images = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}
