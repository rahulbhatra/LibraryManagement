package com.repository

import com.models.Users
import io.micronaut.context.annotation.Executable
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface UserRepository extends CrudRepository<Users, Long> {
    @Executable
    Users findByUsername(String username)
}
