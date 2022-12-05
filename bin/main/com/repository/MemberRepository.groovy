package com.repository

import com.models.Member
import io.micronaut.data.annotation.*
import io.micronaut.data.model.*
import io.micronaut.data.repository.CrudRepository

@Repository
interface MemberRepository extends CrudRepository<Member, Long> {

}
