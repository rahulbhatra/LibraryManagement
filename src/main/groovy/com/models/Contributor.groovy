package com.models

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.ManyToOne

@Entity
@IdClass(ContributorId.class)
class Contributor {
    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Issue issue
    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Person person
}

class ContributorId implements Serializable {
    Issue issue
    Person person
}
