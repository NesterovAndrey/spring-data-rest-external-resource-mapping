package com.objectpartners.domain

import com.objectpartners.rest.annotation.EntityWithRestResource
import com.objectpartners.rest.annotation.RestResourceMapper
import com.objectpartners.rest.resource.RestResourceContext

import javax.persistence.Entity
import javax.persistence.Id

@Entity
@EntityWithRestResource
class User {
    @Id
    Long id

    String username

    @RestResourceMapper(context = RestResourceContext.PROFILE, path="/#id")
    String userProfile
}
