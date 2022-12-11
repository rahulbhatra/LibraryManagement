package com.models


import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
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

    @Transient
    String dobString

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
