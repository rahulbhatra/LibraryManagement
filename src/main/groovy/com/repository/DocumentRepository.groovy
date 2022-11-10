package com.repository

import com.models.Document
import io.micronaut.data.annotation.*
import io.micronaut.data.repository.CrudRepository

@Repository
interface DocumentRepository extends CrudRepository<Document, Long> {

}
