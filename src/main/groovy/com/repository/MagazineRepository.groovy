package com.repository

import com.models.Document
import com.models.Magazine
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface MagazineRepository extends CrudRepository<Magazine, Document> {

}
