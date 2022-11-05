package com.models

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.ManyToOne

@Entity
@IdClass(IssuedInId)
class IssuedIn {
    @Id
    @ManyToOne
    Magazine magazine

    @Id
    @ManyToOne
    Issue issue
}

class IssuedInId implements Serializable {
    Magazine magazine
    Issue issue

    IssuedInId(Magazine magazine, Issue issue) {
        this.magazine = magazine
        this.issue = issue
    }
}
