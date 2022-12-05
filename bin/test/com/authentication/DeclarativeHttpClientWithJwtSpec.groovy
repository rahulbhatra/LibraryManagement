package com.authentication

import com.models.User
import com.nimbusds.jwt.JWTParser
import com.nimbusds.jwt.SignedJWT
import com.repository.UserRepository
import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

@MicronautTest
class DeclarativeHttpClientWithJwtSpec extends Specification {

    @AutoCleanup
    @Shared
    EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer, [:])

    @Inject
    AppClient appClient

    @Shared
    UserRepository userRepository = embeddedServer.applicationContext.getBean(UserRepository)

    def setupSpec() {
//        final Users user = new Users(
//                username: "sherlock",
//                password: "password"
//        )
//        Users persistedUser = userRepository.save(user)
//        assert persistedUser.id
    }
//
//
//    def cleanupSpec() {
//        Users user = userRepository.findByUsername("sherlock").get()
//        userRepository.delete(user)
//
//        Users notPersistedUser = userRepository.findByUsername("sherlock").get()
//        assert notPersistedUser.id == null
//    }

    void "Verify JWT authentication works with declarative @Client"() {
        when: 'Login endpoint is called with valid credentials'
        User user = new User(
            username: "sherlock",
            password: "password"
        )
        userRepository.save(user)

        UsernamePasswordCredentials creds = new UsernamePasswordCredentials("sherlock", "password")
        BearerAccessRefreshToken loginRsp = appClient.login(creds)

        then:
        loginRsp
        loginRsp.accessToken
        JWTParser.parse(loginRsp.accessToken) instanceof SignedJWT

        when:
        String msg = appClient.home("Bearer $loginRsp.accessToken")

        then:
        msg == 'sherlock'

        cleanup:
        userRepository.deleteAll()
    }
}
