package com.controller

import com.models.Users
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/user")
class UserController {

    @Get("/")
    Users getUser() {
        return new Users(
                firstName: 'Rahul',
                middleName: 'None',
                lastName: 'Sharma',
        )
    }

}
