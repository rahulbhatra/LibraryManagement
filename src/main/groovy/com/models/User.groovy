package com.models

import io.micronaut.core.annotation.NonNull
import io.micronaut.core.convert.format.Format

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import java.text.DateFormat
import java.time.LocalDate

@Entity(name = 'users')
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String firstName
    String middleName
    String lastName
    String phoneNumber

    LocalDate dob
    int age
    String address1
    String address2
    String city
    String state
    String zipCode

    @Column(unique = true)
    String username
    String password

    @Enumerated(EnumType.STRING)
    @NotNull
    @NotBlank
    UserType userType
}

enum UserType {
    LIBRARIAN, MEMBER
}
