package com.models

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.ManyToOne
import java.time.Instant

@Entity
@IdClass(BorrowReturnId)
class BorrowReturn {
    @Id
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    Member borrowedBy

    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Copy copy
    Instant borrowDate

    Instant dueDate // borrowDate + 14 days
    Instant returnDate
    boolean isOverdue
}

class BorrowReturnId implements Serializable {
    Member borrowedBy
    Copy copy
    Instant borrowDate
}
