package com.models

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity
class JournalArticle implements Serializable {

    @Id
    @OneToOne(fetch = FetchType.EAGER)
    Document document

    String title

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    Journal journal
}
