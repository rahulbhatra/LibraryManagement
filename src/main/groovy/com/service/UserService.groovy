package com.service

import com.models.Users
import com.repository.UserRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class UserService {
    @Inject
    UserRepository userRepository

    Users addUser(Users users) {
        userRepository.save(users)
    }

    Boolean verifyUser(String username, String password) {
        Users user = userRepository.findByUsername(username)
        if (user) {
            return password.equalsIgnoreCase(user.password)
        }
        return false
    }
}
