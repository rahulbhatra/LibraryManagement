package com.models

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity
class Thesis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    String topic

    @OneToOne(fetch = FetchType.LAZY)
    Document document

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Publisher publishedBy
}
