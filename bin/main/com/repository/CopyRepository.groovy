package com.repository

import com.models.Copy
import io.micronaut.data.annotation.*
import io.micronaut.data.repository.CrudRepository

@Repository
interface CopyRepository extends CrudRepository<Copy, Long> {

}
