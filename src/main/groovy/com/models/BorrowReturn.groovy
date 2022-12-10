package com.models

import io.micronaut.core.annotation.Introspected

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.ManyToOne
import javax.persistence.Transient
import java.time.Instant

@Entity
@IdClass(BorrowReturnId)
class BorrowReturn {
    @Id
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    Member borrowedBy

    @Id
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    Copy copy

    @Id
    Instant borrowDate
    @Transient
    String borrowDateString

    Instant dueDate // borrowDate + 14 days
    @Transient
    String dueDateString

    Instant returnDate
    @Transient
    String returnDateString

    boolean isOverdue
}

@Introspected
class BorrowReturnId implements Serializable {
    Member borrowedBy
    Copy copy
    Instant borrowDate
}
