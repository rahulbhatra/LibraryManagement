package com.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String firstName
    String middleName
    String lastName
    String phoneNumber
    int dob
    int age
    String address1
    String address2
    String city
    String state
    String zipCode

    @Column(unique = true)
    String username
    String password
}
