package com.repository

import com.models.User
import io.micronaut.context.annotation.Executable
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface UserRepository extends CrudRepository<User, Long> {
    @Executable
    Optional<User> findByUsername(String username)
}
