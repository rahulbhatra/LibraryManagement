package com.controller

import com.models.Book
import com.models.BorrowReturn
import com.models.BorrowReturnId
import com.models.Copy
import com.repository.BorrowReturnRepository
import com.repository.CopyRepository
import com.repository.MemberRepository
import com.repository.UserRepository
import groovy.transform.CompileStatic
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Status
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.security.utils.SecurityService
import jakarta.inject.Inject

import java.time.Instant
import java.time.temporal.ChronoUnit

@Controller("/copy")
@Secured(SecurityRule.IS_AUTHENTICATED)
@CompileStatic
class CopyController {

    @Inject
    CopyRepository copyRepository

    @Inject SecurityService securityService

    @Inject UserRepository userRepository

    @Inject MemberRepository memberRepository

    @Inject BorrowReturnRepository borrowReturnRepository

    @Get(uri="/", produces="text/plain")
    String index() {
        "Example Response"
    }

    @Post("/")
    Copy createCopy(Copy copy) {
        copyRepository.save(copy)
    }

    @Post("/createOrUpdateCopies")
    List<Copy> createOrUpdateCopies(List<Copy> copies) {
        copies.forEach(it -> {
            if (it?.id) {
                it = copyRepository.update(it)
            } else {
                it = copyRepository.save(it)
            }
        })
        return copies
    }

    @Post("/createOrUpdateBorrowReturn")
    @Status(HttpStatus.OK)
    HttpResponse createOrUpdateBorrowReturn(BorrowReturn borrowReturn) {
        def username = securityService.username()
        if (!username.isPresent()) {
            return HttpResponse.status(HttpStatus.BAD_REQUEST, "User is not a member or not logged In!")
        }
        def user = userRepository.findByUsername(username.get())
        if (user.isPresent()) {
            def member = memberRepository.findById(user.get())
            if (member.isPresent()) {
                borrowReturn.borrowedBy = member.get()
                borrowReturn.dueDate = borrowReturn.borrowDate.plus(14, ChronoUnit.DAYS)
                BorrowReturnId borrowReturnId = new BorrowReturnId(
                        borrowedBy: borrowReturn.borrowedBy,
                        copy: borrowReturn.copy,
                        borrowDate: borrowReturn.borrowDate,
                )
                borrowReturn = borrowReturnRepository.update(borrowReturn)
                return HttpResponse.ok(borrowReturn)
            }
        }
        return HttpResponse.status(HttpStatus.BAD_REQUEST, "User is not a member or not logged In!")
    }

    @Get("/getAllBorrowed")
    @Status(HttpStatus.OK)
    HttpResponse getAllBorrowed() {
        def username = securityService.username()
        if (!username.isPresent()) {
            return HttpResponse.status(HttpStatus.BAD_REQUEST, "User is not a member or not logged In!")
        }
        def user = userRepository.findByUsername(username.get())
        if (user.isPresent()) {
            def member = memberRepository.findById(user.get())
            if (!member.isPresent()) {
                return HttpResponse.status(HttpStatus.BAD_REQUEST, "User is not a member or not logged In!")
            }
            def borrowedCopies = borrowReturnRepository.findByBorrowedBy(member.get())
            def now = Instant.now()
            borrowedCopies.forEach(borrowedCopy -> {
                borrowedCopy.borrowDateString = borrowedCopy.borrowDate?.toString()
                borrowedCopy.returnDateString = borrowedCopy.returnDate?.toString()
                borrowedCopy.dueDateString = borrowedCopy.dueDate?.toString()
                borrowedCopy.isOverdue = now.isAfter(borrowedCopy.dueDate)
            })
            return HttpResponse.ok(borrowedCopies)
        }
        return HttpResponse.status(HttpStatus.BAD_REQUEST, "User is not a member or not logged In!")
    }
}