package com.repository

import com.models.Librarian
import com.models.User
import io.micronaut.context.annotation.Executable
import io.micronaut.data.annotation.*
import io.micronaut.data.repository.CrudRepository

@Repository
interface LibrarianRepository extends CrudRepository<Librarian, Long> {

//    @Query("select l from Librarian l where l.librarian_info_id = ?1")
    @Executable
    Optional<Librarian> findByUser(User user)
}
