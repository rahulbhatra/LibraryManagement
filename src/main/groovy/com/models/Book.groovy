package com.models

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity
class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    @OneToOne(fetch = FetchType.EAGER)
    Document document
    String title
    int edition
    int year
    String isbn
    String category
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    Publisher publishedBy
}
