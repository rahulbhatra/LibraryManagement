package com.controller

import com.models.Book
import com.models.Document
import com.models.DocumentType
import com.service.DocumentService
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.annotation.Status
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.security.utils.SecurityService
import jakarta.inject.Inject

@Controller("/document")
@Secured(SecurityRule.IS_AUTHENTICATED)
class DocumentController {

    @Inject
    DocumentService documentService

    @Get("/getAll/book")
    List<Book> getAllDocuments(@QueryValue(defaultValue = '') String title) {
        documentService.getAllBooks(title)
    }

    @Post("/")
    @Status(HttpStatus.CREATED)
    Document createDocument(Document document) {
        documentService.createDocument(document)
    }

    @Post("/book")
    @Status(HttpStatus.CREATED)
    Book createBook(Book book) {
        documentService.createBook(book)
    }
}
