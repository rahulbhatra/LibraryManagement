package com.repository

import com.models.RefreshTokenEntity
import io.micronaut.core.annotation.NonNull
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

import javax.transaction.Transactional
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import java.time.Instant

@Repository
interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, Long> {

    @Transactional
    RefreshTokenEntity save(@NonNull @NotBlank String username,
                            @NonNull @NotBlank String refreshToken,
                            @NonNull @NotNull Boolean revoked,
                            @NonNull @NotNull Instant dateCreated)

    Optional<RefreshTokenEntity> findByRefreshToken(@NonNull @NotBlank String refreshToken)

    long updateByUsername(@NonNull @NotBlank String username,
                          boolean revoked)
}
