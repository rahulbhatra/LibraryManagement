package com.service

import com.models.Author
import com.models.Book
import com.models.Document
import com.models.DocumentType
import com.models.Keyword
import com.models.Librarian
import com.models.Person
import com.models.Publisher
import com.repository.AuthorRepository
import com.repository.BookRepository
import com.repository.DocumentRepository
import com.repository.KeywordRepository
import com.repository.PersonRepository
import com.repository.PublisherRepository
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

    @Inject PublisherRepository publisherRepository

    @Inject BookRepository bookRepository

    @Inject PersonRepository personRepository

    @Inject AuthorRepository authorRepository

    @Inject KeywordRepository keywordRepository

    List<Book> getAllBooks(String title) {
        List<Book> books
        if (title?.length() > 0) {
            books = bookRepository.findByTitleContainingIgnoreCase(title)
        } else {
            books = bookRepository.findAll()
        }
        return books
    }

    List<Document> getAll() {
        List<Document> documents = documentRepository.findAll()
        return documents
    }

    Document createDocument(Document document) {
        document = updateDocumentInfo(document)
        document = updateCreateFields(document)
        document = updateUpdateFields(document)
        return documentRepository.save(document)
    }

    Book createBook(Book book) {
        Document document = new Document(
                documentType: DocumentType.BOOK
        )
        book.document = createDocument(document)
        Publisher publisher = new Publisher(
                publisherName: 'publisher'
        )
        book.publishedBy = publisherRepository.save(publisher)
        Person person = new Person(
                firstName: 'Rahul',
                middleName: 'Sharma',
                lastName: 'Sharma',
        )
        personRepository.save(person)
        Author author = new Author(
                person: person,
                document: document
        )
        book = bookRepository.save(book)
        return book
    }

    Document updateCreateFields(Document document) {
        def username = securityService.username()
        def librarian = librarianService.getByUserName(username.get())
        document.createdBy = librarian
        document.createdDate = Instant.now()
        return document
    }

    Document updateUpdateFields(Document document) {
        def username = securityService.username()
        def librarian = librarianService.getByUserName(username.get())
        document.updatedBy = librarian
        document.updatedDate = Instant.now()
        return document
    }

    Document updateDocumentInfo(Document document) {
        switch (document.documentType) {
            case Book:
                document.book = document.book.id ? bookRepository.update(document.book) : bookRepository.save(document.book)
                break
        }
        return document
    }

}
