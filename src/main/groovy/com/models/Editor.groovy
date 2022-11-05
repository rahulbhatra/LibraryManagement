package com.models

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.ManyToOne

@Entity
@IdClass(value = EditorId.class)
class Editor {
    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Issue issue
    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Person person
}

class EditorId implements Serializable {
    Issue issue
    Person person

    EditorId(Issue issue, Person person) {
        this.issue = issue
        this.person = person
    }
}


