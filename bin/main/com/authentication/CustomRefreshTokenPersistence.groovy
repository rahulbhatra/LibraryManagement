package com.authentication

import com.models.RefreshTokenEntity
import com.repository.RefreshTokenRepository
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.errors.OauthErrorResponseException
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent
import io.micronaut.security.token.refresh.RefreshTokenPersistence
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink

import java.time.Instant

import static io.micronaut.security.errors.IssuingAnAccessTokenErrorCode.INVALID_GRANT

@Singleton
class CustomRefreshTokenPersistence implements RefreshTokenPersistence {

    private final RefreshTokenRepository refreshTokenRepository

    CustomRefreshTokenPersistence(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository
    }

    @Override
    void persistToken(RefreshTokenGeneratedEvent event) {
        if (event?.refreshToken && event?.authentication?.name) {
            String payload = event.refreshToken
            refreshTokenRepository.save(event.authentication.name, payload, false, Instant.now())
        }
    }

    @Override
    Publisher<Authentication> getAuthentication(String refreshToken) {
        Flux.create(emitter -> {
            Optional<RefreshTokenEntity> tokenOpt = refreshTokenRepository.findByRefreshToken(refreshToken)
            if (tokenOpt.isPresent()) {
                RefreshTokenEntity token = tokenOpt.get()
                if (token.getRevoked()) {
                    emitter.error(new OauthErrorResponseException(INVALID_GRANT, "refresh token revoked", null))
                } else {
                    emitter.next(Authentication.build(token.username))
                    emitter.complete()
                }
            } else {
                emitter.error(new OauthErrorResponseException(INVALID_GRANT, "refresh token not found", null))
            }
        }, FluxSink.OverflowStrategy.ERROR)
    }
}