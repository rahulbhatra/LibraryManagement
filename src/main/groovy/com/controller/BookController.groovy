package com.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/book")
class BookController {

    @Get(uri="/", produces="text/plain")
    String index() {
        "Example Response"
    }
}