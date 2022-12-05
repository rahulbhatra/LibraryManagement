package com.controller

import com.models.Book
import com.models.Document
import com.models.DocumentType
import com.models.Librarian
import com.models.User
import com.models.UserType
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDate

import static io.micronaut.http.HttpStatus.OK

@MicronautTest
class DocumentControllerSpec extends Specification {

    @Shared @AutoCleanup @Inject @Client("/")
    HttpClient client

    void "create document"() {
        given:
        UUID randomUUID = UUID.randomUUID();

        def generatedUsername = randomUUID.toString().replaceAll("_", "")
        User users = new User(
                username: generatedUsername + "@gmail.com",
                password: "12345",
                dob: LocalDate.of(1997, 10, 25),
                userType: UserType.LIBRARIAN
        )
        HttpRequest userRequest = HttpRequest.POST("/sign-up", users)
        def userResponse = client.toBlocking().retrieve(userRequest, User)

        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(users.username, users.password)
        HttpRequest request = HttpRequest.POST('/login', creds)
        HttpResponse<BearerAccessRefreshToken> rsp = client.toBlocking().exchange(request, BearerAccessRefreshToken)

        BearerAccessRefreshToken bearerAccessRefreshToken = rsp.body()
        bearerAccessRefreshToken.username == 'sherlock'
        bearerAccessRefreshToken.accessToken

        Librarian librarian = new Librarian(
                user: userResponse
        )
        HttpRequest librarianRequest = HttpRequest.POST("/librarian", librarian)
        def librarianResponse = client.toBlocking().retrieve(librarianRequest, Librarian)

        Document document = new Document(
                hierarchicalClassification: 'classification',
                createdBy: librarianResponse,
                documentType: DocumentType.BOOK
        )
        HttpRequest httpRequest = HttpRequest.POST("/document", document).bearerAuth(bearerAccessRefreshToken.accessToken)
        def response = client.toBlocking().exchange(httpRequest, Document)

        expect:
        response.status == HttpStatus.CREATED
    }

    void "create book"() {
        given:
        UUID randomUUID = UUID.randomUUID();

        def generatedUsername = randomUUID.toString().replaceAll("_", "")
        User users = new User(
                username: generatedUsername + "@gmail.com",
                password: "12345",
                dob: LocalDate.of(1997, 10, 25),
                userType: UserType.LIBRARIAN
        )
        HttpRequest userRequest = HttpRequest.POST("/sign-up", users)
        def userResponse = client.toBlocking().retrieve(userRequest, User)

        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(users.username, users.password)
        HttpRequest request = HttpRequest.POST('/login', creds)
        HttpResponse<BearerAccessRefreshToken> rsp = client.toBlocking().exchange(request, BearerAccessRefreshToken)

        BearerAccessRefreshToken bearerAccessRefreshToken = rsp.body()
        bearerAccessRefreshToken.username == 'sherlock'
        bearerAccessRefreshToken.accessToken

        Librarian librarian = new Librarian(
                user: userResponse
        )
        HttpRequest librarianRequest = HttpRequest.POST("user/librarian", librarian).bearerAuth(bearerAccessRefreshToken.accessToken)
        def librarianResponse = client.toBlocking().retrieve(librarianRequest, Librarian)

        Book book = new Book(
                title: 'rahul sharma',
                document: new Document(
                        hierarchicalClassification: 'classification',
                        createdBy: librarianResponse,
                        documentType: DocumentType.BOOK
                )
        )
        HttpRequest httpRequest = HttpRequest.POST("/document/book", book).bearerAuth(bearerAccessRefreshToken.accessToken)
        def response = client.toBlocking().exchange(httpRequest, Book)

        expect:
        response.status == HttpStatus.CREATED
    }
}
