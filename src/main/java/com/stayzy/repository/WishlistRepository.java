package com.stayzy.repository;

import com.stayzy.model.Wishlist;
import com.stayzy.model.User;
import com.stayzy.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUser(User user);
    Optional<Wishlist> findByUserAndProperty(User user, Property property);
    void deleteByUserAndProperty(User user, Property property);
}
