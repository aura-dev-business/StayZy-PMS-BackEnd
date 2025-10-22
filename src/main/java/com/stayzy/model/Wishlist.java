package com.stayzy.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Entity
@Table(name = "wishlist", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "property_id"})
})

public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Column(name = "created_at")
    private Long createdAt = System.currentTimeMillis();

    public Wishlist() {}

    public Wishlist(User user, Property property) {
        this.user = user;
        this.property = property;
        this.createdAt = System.currentTimeMillis();
    }

    // Getters & Setters
    public Long getId() { return id; }
    public User getUser() { return user; }
    public Property getProperty() { return property; }
    public Long getCreatedAt() { return createdAt; }

    public void setUser(User user) { this.user = user; }
    public void setProperty(Property property) { this.property = property; }

    // Optional: if you ever want to read it back as LocalDateTime
    @Transient
    public LocalDateTime getCreatedAtDateTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(createdAt), ZoneOffset.UTC);
    }
}
