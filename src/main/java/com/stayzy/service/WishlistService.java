package com.stayzy.service;

import com.stayzy.model.*;
import com.stayzy.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepo;
    private final PropertyRepository propertyRepo;
    private final UserRepository userRepo;

    public WishlistService(WishlistRepository wishlistRepo, PropertyRepository propertyRepo, UserRepository userRepo) {
        this.wishlistRepo = wishlistRepo;
        this.propertyRepo = propertyRepo;
        this.userRepo = userRepo;
    }

    // Add to wishlist
    public Wishlist addToWishlist(UUID userId, UUID propertyId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Property property = propertyRepo.findById(propertyId).orElseThrow(() -> new RuntimeException("Property not found"));

        // Avoid duplicates
        return wishlistRepo.findByUserAndProperty(user, property)
                .orElseGet(() -> wishlistRepo.save(new Wishlist(user, property)));
    }

    // Remove from wishlist
    public void removeFromWishlist(UUID userId, UUID propertyId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Property property = propertyRepo.findById(propertyId).orElseThrow(() -> new RuntimeException("Property not found"));
        wishlistRepo.deleteByUserAndProperty(user, property);
    }

    // Get user's wishlist
    public List<Wishlist> getUserWishlist(UUID userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return wishlistRepo.findByUser(user);
    }
}