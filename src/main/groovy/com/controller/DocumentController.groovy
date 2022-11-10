package com.controller

import com.models.Document
import com.service.DocumentService
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Status
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import jakarta.inject.Inject

@Controller("/document")
@Secured(SecurityRule.IS_ANONYMOUS)
class DocumentController {

    @Inject
    DocumentService documentService

    @Get("/")
    List<Document> getAllDocuments() {
        documentService.getAll()
    }

    @Post("/")
    @Status(HttpStatus.CREATED)
    Document createDocument(Document document) {
        documentService.createDocument(document)
    }
}
