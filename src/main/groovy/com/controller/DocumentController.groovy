package com.controller

import com.models.Book
import com.models.Document
import com.models.SearchBy
import com.repository.BookRepository
import com.repository.DocumentRepository
import com.service.DocumentService
import groovy.transform.CompileStatic
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import jakarta.inject.Inject

@Controller("/document")
@Secured(SecurityRule.IS_AUTHENTICATED)
@CompileStatic
class DocumentController {

    @Inject
    DocumentService documentService

    @Inject BookRepository bookRepository

    @Inject DocumentRepository documentRepository

    @Post("/getAll/book")
    List<Book> getAllBooks(SearchBy searchBy) {
        documentService.getAllBooks(searchBy)
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

    @Put("/book")
    @Status(HttpStatus.OK)
    Book updateBook(Book book) {
        documentService.updateBook(book)
    }

    @Delete("/")
    @Status(HttpStatus.OK)
    HttpResponse removeDocument(Long id) {
        def document = documentRepository.findById(id)
        if (!document.isPresent()) {
            return HttpResponse.status(HttpStatus.BAD_REQUEST, "document not present")
        } else {
            def doc = document.get()
            documentRepository.delete(doc)
            return HttpResponse.ok(true)
        }
    }
}
