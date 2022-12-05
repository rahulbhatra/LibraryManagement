package com.models

import io.micronaut.core.annotation.NonNull
import io.micronaut.data.annotation.DateCreated

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import java.time.Instant

@Entity
class RefreshTokenEntity {

    @Id
    @GeneratedValue
    @NonNull
    Long id

    @NonNull
    @NotBlank
    String username

    @NonNull
    @NotBlank
    String refreshToken

    @NonNull
    @NotNull
    Boolean revoked

    @DateCreated
    @NonNull
    @NotNull
    Instant dateCreated
}
