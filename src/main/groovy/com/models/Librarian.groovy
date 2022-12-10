package com.models


import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
class Librarian implements Serializable {

    @Id
    @OneToOne(fetch = FetchType.EAGER)
    User user

}
