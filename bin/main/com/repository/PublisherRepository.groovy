package com.repository

import com.models.Publisher
import io.micronaut.data.annotation.*
import io.micronaut.data.repository.CrudRepository

@Repository
interface PublisherRepository extends CrudRepository<Publisher, Long> {

}
