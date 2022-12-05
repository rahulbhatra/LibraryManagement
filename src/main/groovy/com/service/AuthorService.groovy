package com.service

import com.models.Author
import com.models.Document
import com.models.Person
import com.repository.AuthorRepository
import com.repository.PersonRepository
import groovy.transform.CompileStatic
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
@CompileStatic
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

    Author updateAuthor(Person person, Document document) {
        if (person?.id) {
            person = personRepository.update(person)
        } else {
            person = personRepository.save(person)
        }

        Author author = new Author(
                person: person,
                document: document
        )
        author = authorRepository.update(author)
        return author
    }
}
