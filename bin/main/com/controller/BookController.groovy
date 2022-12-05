package com.controller

import com.models.Book
import com.service.BookService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import jakarta.inject.Inject

@Controller("/book")
@Secured(SecurityRule.IS_ANONYMOUS)
class BookController {

    @Inject
    BookService bookService

    @Get(uri="/", produces="text/plain")
    String index() {
        "Example Response"
    }

    @Post("/createBook")
    Book createBook() {
        bookService.createBook()
    }
}