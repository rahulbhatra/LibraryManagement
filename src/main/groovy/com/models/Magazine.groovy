package com.models

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity
class Magazine implements Serializable {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    Document document

    String name
    int edition

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Publisher publishedBy
}
