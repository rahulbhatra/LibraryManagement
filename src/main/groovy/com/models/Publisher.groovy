package com.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String publisherName
}
