package com.repository

import com.models.JournalArticle
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface JournalArticleRepository extends CrudRepository<JournalArticle, Long> {

}
