package com.controller

import com.models.User
import com.service.UserService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import jakarta.inject.Inject

@Controller("/user")
@Secured(SecurityRule.IS_AUTHENTICATED)
class UserController {

    @Inject
    UserService userService

    @Get("/")
    User getUser() {
        return new User(
                firstName: 'Rahul',
                middleName: 'None',
                lastName: 'Sharma',
        )
    }

    @Post("/verifyUser")
    Boolean verifyUser(String username, String password) {
        userService.verifyUser(username, password)
    }

    @Post("/")
    User save(User user) {
        userService.addUser(user)
    }
}
