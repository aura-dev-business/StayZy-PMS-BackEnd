package com.stayzy.controller;

import com.stayzy.model.Wishlist;
import com.stayzy.service.WishlistService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin(origins = "*")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/add")
    public Wishlist addToWishlist(@RequestParam String userId, @RequestParam String propertyId) {
        UUID userUUID = UUID.fromString(userId);
        UUID propertyUUID = UUID.fromString(propertyId);
        return wishlistService.addToWishlist(userUUID, propertyUUID);
    }

    @DeleteMapping("/remove")
    public void removeFromWishlist(@RequestParam String userId, @RequestParam String propertyId) {
        UUID userUUID = UUID.fromString(userId);
        UUID propertyUUID = UUID.fromString(propertyId);
        wishlistService.removeFromWishlist(userUUID, propertyUUID);
    }

    @GetMapping("/{userId}")
    public List<Wishlist> getUserWishlist(@PathVariable String userId) {
        UUID userUUID = UUID.fromString(userId);
        return wishlistService.getUserWishlist(userUUID);
    }
}
