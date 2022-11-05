package com.models

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import java.time.Instant

@Entity
class Document implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String hierarchicalClassification
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Librarian createdBy
    Instant createdDate
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    Librarian updatedBy
    Instant updatedDate
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    Librarian deletedBy
    Instant deletedDate
}

enum DocumentType {
    BOOK, MAGAZINE, JOURNAL_ARTICLE, THESIS, REPORT
}
