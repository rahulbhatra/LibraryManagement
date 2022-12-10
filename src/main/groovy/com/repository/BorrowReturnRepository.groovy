package com.repository

import com.models.BorrowReturn
import com.models.BorrowReturnId
import com.models.Copy
import com.models.Document
import com.models.Member
import io.micronaut.context.annotation.Executable
import io.micronaut.data.annotation.*
import io.micronaut.data.repository.CrudRepository

@Repository
interface BorrowReturnRepository extends CrudRepository<BorrowReturn, BorrowReturnId> {
    @Executable
    List<BorrowReturn> findByReturnDateIsNull()

    @Executable
    List<BorrowReturn> findByReturnDateIsNullAndBorrowedBy(Member borrowedBy)

    @Executable
    List<BorrowReturn> findByBorrowedBy(Member borrowedBy)
}
