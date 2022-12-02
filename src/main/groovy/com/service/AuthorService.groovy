package com.service

import com.models.Author
import com.models.Document
import com.models.Person
import com.repository.AuthorRepository
import com.repository.PersonRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class AuthorService {
    @Inject PersonRepository personRepository
    @Inject AuthorRepository authorRepository

    Author createAuthor(Person person, Document document) {
        personRepository.save(person)
        Author author = new Author(
                person: person,
                document: document
        )
        author = authorRepository.update(author)
        return author
    }
}
