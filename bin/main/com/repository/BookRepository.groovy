package com.repository

import com.models.Book
import com.models.Document
import io.micronaut.context.annotation.Executable
import io.micronaut.data.annotation.*
import io.micronaut.data.repository.CrudRepository

@Repository
interface BookRepository extends CrudRepository<Book, Long> {
    @Executable
    Optional<Book> findByDocument(Document document)

    @Executable
    List<Book> findByTitleContainingIgnoreCase(String title)
}
