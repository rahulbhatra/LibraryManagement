package com.controller

import com.models.Librarian
import com.models.Member
import com.models.User
import com.repository.MemberRepository
import com.service.UserService
import groovy.transform.CompileStatic
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import jakarta.inject.Inject

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
        userService.addUser(user)
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
        userService.getAllLibrarians()
    }

    @Get("getAllMembers")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    List<Member> getAllMembers() {
        memberRepository.findAll().asList()
    }
}
