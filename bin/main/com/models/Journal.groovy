package com.models

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String title
    int edition

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Publisher publishedBy
}
