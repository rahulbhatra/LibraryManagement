package com.repository

import com.models.Person
import io.micronaut.data.annotation.*
import io.micronaut.data.repository.CrudRepository

@Repository
interface PersonRepository extends CrudRepository<Person, Long> {

}
