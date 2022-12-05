package com.service

import com.models.Author
import com.models.Book
import com.models.Document
import com.models.DocumentType
import com.models.Person
import com.models.Publisher
import com.repository.AuthorRepository
import com.repository.BookRepository
import com.repository.DocumentRepository
import com.repository.KeywordRepository
import com.repository.PersonRepository
import com.repository.PublisherRepository
import groovy.transform.CompileStatic
import io.micronaut.security.utils.SecurityService
import jakarta.inject.Inject
import jakarta.inject.Singleton

import javax.transaction.Transactional
import java.time.Instant

@Singleton
@CompileStatic
class DocumentService {

    @Inject
    SecurityService securityService

    @Inject
    DocumentRepository documentRepository

    @Inject PublisherRepository publisherRepository

    @Inject BookRepository bookRepository

    @Inject PersonRepository personRepository

    @Inject AuthorRepository authorRepository

    @Inject AuthorService authorService

    @Inject KeywordRepository keywordRepository

    @Inject UserService userService

    List<Book> getAllBooks(String title) {
        List<Book> books
        if (title?.length() > 0) {
            books = bookRepository.findByTitleContainingIgnoreCase(title)
        } else {
            books = bookRepository.findAll().asList()
        }
        books.forEach(it -> {
            def authors = authorRepository.findByDocument(it.document)
            it.authorsList = authors
        })
        return books
    }

    List<Document> getAll() {
        List<Document> documents = documentRepository.findAll().asList()
        return documents
    }

    Document createDocument(Document document) {
        document = updateDocumentInfo(document)
        document = updateCreateFields(document)
        document = updateUpdateFields(document)
        return documentRepository.save(document)
    }

    @Transactional
    Book createBook(Book book) {
        Document document = new Document(
                documentType: DocumentType.BOOK
        )
        book.document = createDocument(document)
        List<Person> persons = book.authors
        List<Author>  authors = []
        persons.forEach(it -> authors.add(authorService.createAuthor(it, book.document)))
        book.authorsList = authors
        Publisher publisher = new Publisher(
                publisherName: 'publisher'
        )
        book.publishedBy = publisherRepository.save(publisher)
        book = bookRepository.save(book)
        return book
    }

    @Transactional
    Book updateBook(Book book) {
        List<Person> persons = book.authors
        List<Author>  authors = []
        persons.forEach(it -> authors.add(authorService.updateAuthor(it, book.document)))
        book = bookRepository.update(book)
        book.authorsList = authors
        return book
    }

    Document updateCreateFields(Document document) {
        def username = securityService.username()
        def librarian = userService.getByUserName(username.get())
        document.createdBy = librarian
        document.createdDate = Instant.now()
        return document
    }

    Document updateUpdateFields(Document document) {
        def username = securityService.username()
        def librarian = userService.getByUserName(username.get())
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
