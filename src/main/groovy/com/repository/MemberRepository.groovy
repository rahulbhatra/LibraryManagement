package com.repository

import com.models.Member
import com.models.User
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface MemberRepository extends CrudRepository<Member, User> {

}
