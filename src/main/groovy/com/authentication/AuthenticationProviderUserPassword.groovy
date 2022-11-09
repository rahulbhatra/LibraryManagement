package com.authentication

import com.models.Users
import com.repository.UserRepository
import io.micronaut.core.annotation.Nullable
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink

@Singleton
class AuthenticationProviderUserPassword implements AuthenticationProvider {

    @Inject
    UserRepository userRepository

    @Override
    Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest,
                                                   AuthenticationRequest<?, ?> authenticationRequest) {
        Flux.create(emitter -> {
            Optional<Users> user = userRepository.findByUsername(authenticationRequest.identity)
            if (user.isPresent() && authenticationRequest.identity == user.get().username && authenticationRequest.secret == user.get().password) {
                emitter.next(AuthenticationResponse.success((String) authenticationRequest.identity))
                emitter.complete()
            } else {
                emitter.error(AuthenticationResponse.exception())
            }
        }, FluxSink.OverflowStrategy.ERROR)
    }
}
