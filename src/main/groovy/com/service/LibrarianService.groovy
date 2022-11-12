package com.service

import com.models.Librarian
import com.models.User
import com.models.UserType
import com.repository.LibrarianRepository
import com.repository.UserRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class LibrarianService {
    @Inject
    LibrarianRepository librarianRepository

    @Inject
    UserService userService

    Librarian createNewLibrarian(Librarian librarian) {
        User librarianInfo = librarian.librarianInfo
        librarianInfo.userType = UserType.LIBRARIAN
        if (!librarianInfo.id) {
            librarian.librarianInfo = userService.addUser(librarianInfo)
        }
        librarianRepository.save(librarian)
    }

    List<Librarian> getAllLibrarians() {
        librarianRepository.findAll()
    }
}
