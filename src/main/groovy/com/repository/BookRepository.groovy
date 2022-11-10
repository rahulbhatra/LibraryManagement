package com.repository

import com.models.Book
import io.micronaut.data.annotation.*
import io.micronaut.data.repository.CrudRepository

@Repository
interface BookRepository extends CrudRepository<Book, Long> {

}
