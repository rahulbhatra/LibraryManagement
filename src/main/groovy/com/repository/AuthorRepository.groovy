package com.repository

import com.models.Author
import com.models.AuthorId
import com.models.Document
import io.micronaut.context.annotation.Executable
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface AuthorRepository extends CrudRepository<Author, AuthorId> {

    @Executable
    List<Author> findByDocument(Document document)

    @Query("select * from Author join Person on person_id = id where CONCAT(first_name, middle_name, last_name) ilike '%:authorName%';")
    List<Author> findByAuthorName(String authorName)

}
