package com.service

import com.models.Librarian
import com.models.Member
import com.models.User
import com.models.UserType
import com.repository.LibrarianRepository
import com.repository.MemberRepository
import com.repository.UserRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class UserService {
    @Inject
    UserRepository userRepository

    @Inject
    LibrarianRepository librarianRepository

    @Inject
    MemberRepository memberRepository

    User addUser(User user, UserType userType) {
        user.userType = userType
        user = userRepository.save(user)
        return user
    }

    Boolean verifyUser(String username, String password) {
        User user = userRepository.findByUsername(username)
        if (user) {
            return password.equalsIgnoreCase(user.password)
        }
        return false
    }

    Librarian getByUserName(String username) {
        def user = userRepository.findByUsername(username)
        if (user.isPresent()) {
            def librarian = librarianRepository.findByUser(user.get())
            if (librarian) {
                return librarian.get()
            }
        }
        return null
    }

    Librarian createNewLibrarian(Librarian librarian) {
        User user = librarian.user
        if (!user.id) {
            librarian.user = addUser(user, UserType.LIBRARIAN)
        }
        librarianRepository.save(librarian)
    }

    Member createNewMember(Member member) {
        User user = member.user
        if (!user.id) {
            member.user = addUser(user, UserType.MEMBER)
        }
        member = memberRepository.save(member)
        return member
    }

    Librarian updateLibrarian(Librarian librarian) {
        User librarianInfo = librarian.user
        librarian.user = userRepository.update(librarianInfo)
        return librarian
    }

    Member updateMember(Member member) {
        User user = member.user
        member.user = userRepository.update(user)
        member = memberRepository.update(member)
        return member
    }

    boolean deleteLibrarian(Librarian librarian) {
        userRepository.delete(librarian.user)
        return true
    }

    boolean deleteMember(Member member) {
        userRepository.delete(member.user)
        return true
    }

    List<Librarian> getAllLibrarians() {
        librarianRepository.findAll()
    }
}
