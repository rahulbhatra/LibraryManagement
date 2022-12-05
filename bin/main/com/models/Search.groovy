package com.models

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.ManyToOne

@Entity
@IdClass(SearchId)
class Search {
    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Document document

    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Member member
}

class SearchId implements Serializable {
    Document document
    Member member

    SearchId(Document document, Member member) {
        this.document = document
        this.member = member
    }
}
