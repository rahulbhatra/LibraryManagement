package com.models

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.ManyToOne

@Entity
@IdClass(AuthorId.class)
class Author {
    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Person person
    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Document document
}

class AuthorId implements Serializable {
    Person person
    Document document

    AuthorId(Person person, Document document) {
        this.person = person
        this.document = document
    }
}
