package com.repository

import com.models.Document
import com.models.Keyword
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface KeywordRepository extends CrudRepository<Keyword, Document> {

}
