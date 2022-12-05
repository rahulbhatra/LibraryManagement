package com.models

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.ManyToOne

@Entity
@IdClass(CiteId)
class Cite {
    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Document citedDocument

    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Document citedByDocument
}

class CiteId implements Serializable {
    Document citedDocument
    Document citedByDocument

    CiteId(Document citedDocument, Document citedByDocument) {
        this.citedDocument = citedDocument
        this.citedByDocument = citedByDocument
    }
}
