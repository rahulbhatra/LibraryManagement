package com.service

import com.models.Author
import com.models.Book
import com.models.Document
import com.models.DocumentType
import com.models.Person
import com.models.Publisher
import com.models.SearchBy
import com.repository.AuthorRepository
import com.repository.BookRepository
import com.repository.BorrowReturnRepository
import com.repository.CopyRepository
import com.repository.DocumentRepository
import com.repository.KeywordRepository
import com.repository.PublisherRepository
import com.repository.UserRepository
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

    @Inject UserRepository userRepository

    @Inject AuthorRepository authorRepository

    @Inject CopyRepository copyRepository

    @Inject AuthorService authorService

    @Inject KeywordRepository keywordRepository

    @Inject UserService userService

    @Inject BorrowReturnRepository borrowReturnRepository

    String contains(String x) {
        return "%" + x + "%"
    }

    List<Book> getAllBooks(SearchBy searchBy) {
        List<Book> books
        searchBy?.author = searchBy?.author?: ''
        searchBy?.title = searchBy?.title?: ''
        if (searchBy?.combinedSearch) {
            books = bookRepository.findByTitleOrAuthorName(contains(searchBy?.searchTerm))
        } else {
            books = bookRepository.findByTitleAndAuthorName(contains(searchBy?.title), contains(searchBy?.author))
        }
        def notReturnedBorrowCopies = borrowReturnRepository.findByReturnDateIsNull()
        def notReturnedCopies = notReturnedBorrowCopies*.copy.id
        books.forEach(it -> {
            def authors = authorRepository.findByDocument(it.document)
            def copies = copyRepository.findByDocument(it.document)
            copies = copies.findAll( copy -> !notReturnedCopies.contains(copy.id))
            it.copies = copies
            it.totalCopies = copies?.size()
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
                document.book =  bookRepository.update(document.book)
                break
        }
        return document
    }

}
