package com.controller

import com.models.Librarian
import com.repository.LibrarianRepository
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
    LibrarianRepository librarianRepository
    @Post("/")
    Librarian save(Librarian librarian) {
        librarianRepository.save(librarian)
    }

    @Get(uri="/", produces="text/plain")
    String index() {
        "Example Response"
    }
}