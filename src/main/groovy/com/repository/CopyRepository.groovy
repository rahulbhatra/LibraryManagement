package com.repository


import com.models.Copy
import com.models.Document
import io.micronaut.context.annotation.Executable
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface CopyRepository extends CrudRepository<Copy, Long> {
    @Executable
    List<Copy> findByDocument(Document document)
}
