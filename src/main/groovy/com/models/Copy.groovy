package com.models

import groovy.transform.CompileStatic

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
@CompileStatic
class Copy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    Document document

    int roomNumber
    int level
}
