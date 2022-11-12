package com.service

import com.models.Book
import com.models.Document
import com.models.DocumentType
import com.models.Librarian
import com.models.Publisher
import com.models.User
import com.repository.BookRepository
import com.repository.DocumentRepository
import com.repository.LibrarianRepository
import com.repository.PublisherRepository
import com.repository.UserRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton

import java.time.Instant
import java.time.LocalDate

@Singleton
class BookService {

    @Inject
    UserRepository userRepository

    @Inject
    LibrarianRepository librarianRepository

    @Inject
    DocumentRepository documentRepository

    @Inject
    PublisherRepository publisherRepository

    @Inject
    BookRepository bookRepository

    Book createBook() {
        User user = new User(
                firstName: 'Rahul',
                lastName: 'Sharma',
                phoneNumber: '312-539-3570',
                dob: LocalDate.of(1997, 10, 25),
                address1: '400 E 33 RD ST',
                address2: 'APT 512',
                city: 'Chicago',
                state: 'Illinois',
                zipCode: '60616',
                username: 'rahulbhatra@gmail.com',
                password: '12345'
        )
        user = userRepository.save(user)

        Librarian librarian = new Librarian(
                librarianInfo: user
        )
        librarian = librarianRepository.save(librarian)

        Document document = new Document(
            hierarchicalClassification: 'tech',
            createdBy: librarian,
            createdDate: Instant.now(),
            documentType: DocumentType.BOOK,
        )
        document = documentRepository.save(document)

        Publisher publisher = new Publisher(
            publisherName: 'publisher'
        )
        publisher = publisherRepository.save(publisher)

        Book book = new Book(
            title: 'CLRS',
            edition: 1,
            year: 2022,
            isbn: 'abcd',
            category: 'Technology',
            publishedBy: publisher
        )
        book = bookRepository.save(book)
        return book
    }
}
