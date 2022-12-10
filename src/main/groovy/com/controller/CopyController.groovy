package com.controller

import com.models.Copy
import com.repository.CopyRepository
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import jakarta.inject.Inject

@Controller("/copy")
@Secured(SecurityRule.IS_ANONYMOUS)
class CopyController {

    @Inject
    CopyRepository copyRepository

    @Get(uri="/", produces="text/plain")
    String index() {
        "Example Response"
    }

    @Post("/")
    Copy createCopy(Copy copy) {
        copyRepository.save(copy)
    }

    @Post("/createOrUpdateCopies")
    List<Copy> createOrUpdateCopies(List<Copy> copies) {
        copies.forEach(it -> {
            if (it?.id) {
                it = copyRepository.update(it)
            } else {
                it = copyRepository.save(it)
            }
        })
        return copies
    }
}