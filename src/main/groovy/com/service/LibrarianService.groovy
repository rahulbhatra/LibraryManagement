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

    @Inject
    UserRepository userRepository

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
        user.userType = UserType.LIBRARIAN
        if (!user.id) {
            librarian.user = userService.addUser(user)
        }
        librarianRepository.save(librarian)
    }

    Librarian updateLibrarian(Librarian librarian) {
        User librarianInfo = librarian.librarianInfo
        librarian.librarianInfo = userRepository.update(librarianInfo)
        return librarian
    }

    List<Librarian> getAllLibrarians() {
        librarianRepository.findAll()
    }
}
