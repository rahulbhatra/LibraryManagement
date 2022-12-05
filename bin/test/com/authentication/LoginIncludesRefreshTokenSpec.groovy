package com.authentication

import com.models.User
import com.nimbusds.jwt.JWTParser
import com.nimbusds.jwt.SignedJWT
import com.repository.UserRepository
import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

@MicronautTest
class LoginIncludesRefreshTokenSpec extends Specification {

    @Inject
    @Client("/")
    HttpClient client

    @AutoCleanup
    @Shared
    EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer, [:])

    @Shared
    UserRepository userRepository = embeddedServer.applicationContext.getBean(UserRepository)

    void "upon successful authentication, the user gets an access token and a refresh token"() {
        when: 'Login endpoint is called with valid credentials'
        User user = new User(
                username: "sherlock",
                password: "password"
        )
        userRepository.save(user)

        UsernamePasswordCredentials creds = new UsernamePasswordCredentials("sherlock", "password")
        HttpRequest request = HttpRequest.POST('/login', creds)
        BearerAccessRefreshToken rsp = client.toBlocking().retrieve(request, BearerAccessRefreshToken)

        then:
        rsp.username == 'sherlock'
        rsp.accessToken
        rsp.refreshToken

        and: 'access token is a JWT'
        JWTParser.parse(rsp.accessToken) instanceof SignedJWT

        cleanup:
        userRepository.deleteAll()
    }

    void "testLoginAccessTokenCanBeUsedOnSecuredEndpoint"() {
        when: 'Login endpoint is called with valid credentials'
        User user = new User(
                username: "sherlock",
                password: "password"
        )
        userRepository.save(user)

        UsernamePasswordCredentials creds = new UsernamePasswordCredentials("sherlock", "password")
        HttpRequest request = HttpRequest.POST("/login", creds);
        HttpResponse<BearerAccessRefreshToken> rsp =
                client.toBlocking().exchange(request, BearerAccessRefreshToken.class);

        BearerAccessRefreshToken bearerAccessRefreshToken = rsp.body();
        String accessToken = bearerAccessRefreshToken.getAccessToken();

        def userRequest = HttpRequest.GET("/user").bearerAuth(accessToken)
        def response = client.toBlocking().retrieve(
                userRequest, User.class);


        then:
        response != null
        response.firstName == 'Rahul'

        cleanup:
        userRepository.deleteAll()
    }
}
