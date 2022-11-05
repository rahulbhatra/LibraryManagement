package com.models

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import java.time.Instant

@Entity
class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    String id

    @OneToOne(fetch = FetchType.LAZY)
    Users memberInfo

    Instant membershipCoverage //last date till member can borrow a document and access library

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Librarian managedBy
}