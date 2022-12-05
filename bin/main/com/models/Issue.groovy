package com.models

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToOne
import java.time.Instant

@Entity
class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String title
    @OneToOne(fetch = FetchType.LAZY)
    Publisher publishedBy
    Instant publishedAt
}
