package com.service

import com.models.Document
import com.models.Librarian
import com.repository.DocumentRepository
import io.micronaut.security.utils.SecurityService
import jakarta.inject.Inject
import jakarta.inject.Singleton

import java.time.Instant

@Singleton
class DocumentService {

    @Inject
    SecurityService securityService

    @Inject
    DocumentRepository documentRepository

    @Inject
    LibrarianService librarianService

    List<Document> getAll() {
        List<Document> documents = documentRepository.findAll()
        return documents
    }

    Document createDocument(Document document) {
        def username = securityService.username()
        def librarian = librarianService.getByUserName(username.get())
        document.createdBy = librarian
        document.createdDate = Instant.now()
        document.updatedBy = librarian
        document.updatedDate = Instant.now()
        return documentRepository.save(document)
    }

}
