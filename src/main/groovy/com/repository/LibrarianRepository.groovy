package com.repository

import com.models.Librarian
import io.micronaut.data.annotation.*
import io.micronaut.data.repository.CrudRepository

@Repository
interface LibrarianRepository extends CrudRepository<Librarian, Long> {

}
