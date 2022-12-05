package com.models

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity
class JournalArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @OneToOne(fetch = FetchType.LAZY)
    Document journalArticleDocument

    String title

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Journal journal
}
