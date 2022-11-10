package com.controller

import com.models.Users
import com.service.UserService
import groovy.transform.CompileStatic
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import jakarta.inject.Inject

import java.security.Principal

@CompileStatic
@Controller("/")
@Secured(SecurityRule.IS_ANONYMOUS)
class HomeController {

    @Inject
    UserService userService

    @Produces(MediaType.TEXT_PLAIN)
    @Get
    @Secured(SecurityRule.IS_AUTHENTICATED)
    String index(Principal principal) {
        principal.name
    }

    @Post("/sign-up")
    Users createUser(Users users) {
        return userService.addUser(users)
    }
}
