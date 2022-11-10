package com.service

import com.models.Document
import com.repository.DocumentRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class DocumentService {

    @Inject
    DocumentRepository documentRepository

    List<Document> getAll() {
        List<Document> documents = documentRepository.findAll()
        return documents
    }

    Document createDocument(Document document) {
        return documentRepository.save(document)
    }

}
