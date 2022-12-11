package com.controller

import com.models.Librarian
import com.models.Member
import com.models.User
import com.repository.MemberRepository
import com.service.UserService
import groovy.transform.CompileStatic
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import jakarta.inject.Inject

import java.time.LocalDate
import java.time.Period

@Controller("/user")
@Secured(SecurityRule.IS_AUTHENTICATED)
@CompileStatic
class UserController {

    @Inject
    UserService userService

    @Inject
    MemberRepository memberRepository

    @Get("/")
    User getUser() {
        return new User(
                firstName: 'Rahul',
                middleName: 'None',
                lastName: 'Sharma',
        )
    }

    @Post("/verifyUser")
    Boolean verifyUser(String username, String password) {
        userService.verifyUser(username, password)
    }

    @Post("/")
    User save(User user) {
        def librarian = userService.createNewLibrarian(new Librarian( user: user))
        return librarian.user
    }

    @Post("/librarian")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    Librarian save(Librarian librarian) {
        userService.createNewLibrarian(librarian)
    }

    @Post("/member")
    Member saveMember(Member member) {
        userService.createNewMember(member)
    }

    @Put("/librarian")
    Librarian updateLibrarian(Librarian librarian) {
        userService.updateLibrarian(librarian)
    }

    @Put("/member")
    Member updateMember(Member member) {
        userService.updateMember(member)
    }

    @Delete("/librarian")
    boolean deleteLibrarian(Librarian librarian) {
        return userService.deleteLibrarian(librarian)
    }

    @Delete("/member")
    boolean deleteMember(Member member) {
        return userService.deleteMember(member)
    }

    @Get("getAllLibrarians")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    List<Librarian> getAllLibrarians() {
        def librarians = userService.getAllLibrarians()
        librarians.forEach(librarian -> {
            if (librarian?.user?.dob) {
                librarian.user.dobString = librarian.user.dob.toString()
                LocalDate now = LocalDate.now()
                Period age = Period.between(librarian.user.dob, now)
                librarian.user.age = age.years
            }
        })
        return librarians
    }

    @Get("getAllMembers")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    List<Member> getAllMembers() {
        def members= memberRepository.findAll().asList()
        members.forEach(member -> {
            if (member?.user?.dob) {
                member.user.dobString = member.user.dob.toString()
                LocalDate now = LocalDate.now()
                Period age = Period.between(member.user.dob, now)
                member.user.age = age.years
            }
        })
        return members
    }
}
