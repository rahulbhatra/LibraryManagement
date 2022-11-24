package com.models

import io.micronaut.core.annotation.NonNull
import io.micronaut.data.annotation.DateCreated

import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import java.time.Instant

@Entity
class Document implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String hierarchicalClassification
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    Librarian createdBy

    @DateCreated
    Instant createdDate

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    Librarian updatedBy
    Instant updatedDate

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    Librarian deletedBy
    Instant deletedDate

    @Enumerated(EnumType.STRING)
    @NotNull
    @NonNull
    @NotBlank
    DocumentType documentType
}

enum DocumentType {
    BOOK, MAGAZINE, JOURNAL_ARTICLE, THESIS, REPORT
}
