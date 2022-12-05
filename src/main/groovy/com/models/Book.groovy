package com.models

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Transient

@Entity
class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    Document document
    String title
    int edition
    int year
    String isbn
    String category
    @ManyToOne(optional = false, fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    Publisher publishedBy

    @Transient
    List<Person> authors

    @Transient
    List<Author> authorsList
}
