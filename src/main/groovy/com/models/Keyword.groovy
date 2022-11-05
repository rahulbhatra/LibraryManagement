package com.models

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Keyword implements Serializable {
    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Document document

    String keyword
}
