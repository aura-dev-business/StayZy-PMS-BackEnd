package com.stayzy.service.impl;

import com.stayzy.dto.UserResponse;
import com.stayzy.model.User;
import com.stayzy.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserResponse getUserProfile(User user) {
        UserResponse response = new UserResponse();
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername());
        response.setTotalBookings(user.getBookings().size());   // assumes getBookings() returns a List
        response.setTotalWishlist(user.getWishlist().size());   // assumes getWishlist() returns a List
        return response;
    }
}
