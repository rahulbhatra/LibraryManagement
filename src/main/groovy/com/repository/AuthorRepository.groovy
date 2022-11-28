package com.repository

import com.models.Author
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface AuthorRepository extends CrudRepository<Author, Long> {

}
