package com.service

import com.models.User
import com.repository.UserRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class UserService {
    @Inject
    UserRepository userRepository

    User addUser(User user) {
        userRepository.save(user)
    }

    Boolean verifyUser(String username, String password) {
        User user = userRepository.findByUsername(username)
        if (user) {
            return password.equalsIgnoreCase(user.password)
        }
        return false
    }
}
