package com.controller

import com.models.Librarian
import com.repository.LibrarianRepository
import com.service.LibrarianService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import jakarta.inject.Inject

@Controller("/librarian")
@Secured(SecurityRule.IS_ANONYMOUS)
class LibrarianController {

    @Inject
    LibrarianService librarianService

    @Post("/")
    Librarian save(Librarian librarian) {
        librarianService.createNewLibrarian(librarian)
    }

    @Get(uri="/", produces="text/plain")
    String index() {
        "Example Response"
    }

    @Get("/getAllLibrarians")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    List<Librarian> getAllLibrarians() {
        librarianService.getAllLibrarians()
    }
}