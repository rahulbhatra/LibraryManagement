package com.models

import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity
class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @OneToOne(fetch = FetchType.LAZY)
    Document reportDocument

    String name

    @Enumerated(value = EnumType.STRING)
    ReportType reportType

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Publisher publishedBy
}

enum ReportType {
    TECHNOLOGY, SPORTS, BUSINESS, ENTERTAINMENT, SCIENCE, FUTURE
}
