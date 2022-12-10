package com.repository

import com.models.Book
import com.models.Document
import io.micronaut.context.annotation.Executable
import io.micronaut.data.annotation.*
import io.micronaut.data.repository.CrudRepository

@Repository
interface BookRepository extends CrudRepository<Book, Document> {
    @Executable
    Optional<Book> findByDocument(Document document)

    @Query(value = "select * from Book where title ilike :title", nativeQuery = true)
    List<Book> findByTitleContainingIgnoreCase(String title)

    @Query(value = "select distinct b.* from book b left join Author a on b.document_id = a.document_id left join Person p on a.person_id = p.id where title ilike :title and CONCAT(first_name, middle_name, last_name) ilike :author", nativeQuery = true)
    List<Book> findByTitleAndAuthorName(String title, String author)

    @Query(value = "select distinct b.* from book b left join Author a on b.document_id = a.document_id left join Person p on a.person_id = p.id where title ilike :searchTerm or CONCAT(first_name, middle_name, last_name) ilike :searchTerm", nativeQuery = true)
    List<Book> findByTitleOrAuthorName(String searchTerm)
}
