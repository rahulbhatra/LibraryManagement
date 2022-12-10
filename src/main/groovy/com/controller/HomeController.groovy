package com.controller

import com.models.Librarian
import com.models.User
import com.models.UserType
import com.repository.LibrarianRepository
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

    @Inject LibrarianRepository librarianRepository

    @Produces(MediaType.TEXT_PLAIN)
    @Get
    @Secured(SecurityRule.IS_AUTHENTICATED)
    String index(Principal principal) {
        principal.name
    }

    @Post("/sign-up")
    User createUser(User users) {
        users = userService.addUser(users, UserType.LIBRARIAN)
        Librarian librarian = new Librarian(
                user: users
        )
        librarianRepository.save(librarian)
        return users
    }
}
