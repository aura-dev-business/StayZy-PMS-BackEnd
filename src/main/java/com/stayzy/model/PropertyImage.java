package com.stayzy.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "property_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PropertyImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    @JsonBackReference
    private Property property;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "is_primary")
    private Boolean isPrimary = false;

    private Integer sortOrder;  // for ordering images (optional)
}





//Required / Core Fields

// id – UUID or Long

// Primary key for the image.

// property – Property (Many-to-One relationship)

// Which property this image belongs to.

// imageUrl – String

// The URL of the image (can be S3 or any storage).

// Optional / Useful Fields

// title / caption – String

// Short description for the image (e.g., “Front view” or “Living room”).

// isFeatured – Boolean

// Mark one image as the “main” or “cover” image for a property.

// sortOrder / position – Integer

// If you want to display images in a specific order.

// createdAt / uploadedAt – Date or LocalDateTime

// When the image was uploaded.

// fileSize / fileType – optional

// Store metadata for validation or display purposes