package com.controller

import com.models.Document
import com.models.DocumentType
import com.models.Librarian
import com.models.Users
import com.repository.LibrarianRepository
import com.repository.UserRepository
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

@MicronautTest
class DocumentControllerSpec extends Specification {

    @Shared @AutoCleanup @Inject @Client("/")
    HttpClient client

    void "create document"() {
        given:
        UUID randomUUID = UUID.randomUUID();

        def generatedUsername = randomUUID.toString().replaceAll("_", "")
        Users users = new Users(
                username: generatedUsername + "@gmail.com",
                password: "12345"
        )
        HttpRequest userRequest = HttpRequest.POST("/sign-up", users)
        def userResponse = client.toBlocking().retrieve(userRequest, Users)


        Librarian librarian = new Librarian(
                librarianInfo: userResponse
        )
        HttpRequest librarianRequest = HttpRequest.POST("/librarian", librarian)
        def librarianResponse = client.toBlocking().retrieve(librarianRequest, Librarian)

        Document document = new Document(
                hierarchicalClassification: 'classification',
                createdBy: librarianResponse,
                documentType: DocumentType.BOOK
        )
        HttpRequest httpRequest = HttpRequest.POST("/document", document)
        def response = client.toBlocking().exchange(httpRequest, Document)

        expect:
        response.status == HttpStatus.CREATED
    }
}
