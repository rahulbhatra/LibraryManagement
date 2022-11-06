package com.controller

import com.models.Users
import com.service.UserService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import jakarta.inject.Inject

@Controller("/user")
class UserController {

    @Inject
    UserService userService

    @Get("/")
    Users getUser() {
        return new Users(
                firstName: 'Rahul',
                middleName: 'None',
                lastName: 'Sharma',
        )
    }

    @Post("/")
    Users createUser(Users users) {
        return userService.addUser(users)
    }

    @Post("/verifyUser")
    Boolean verifyUser(String username, String password) {
        userService.verifyUser(username, password)
    }
}
