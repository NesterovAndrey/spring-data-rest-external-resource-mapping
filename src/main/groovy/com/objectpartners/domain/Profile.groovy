package com.objectpartners.domain

import com.objectpartners.rest.annotation.EntityWithRestResource
import com.objectpartners.rest.annotation.RestResourceMapper
import com.objectpartners.rest.resource.RestResourceApiKey
import com.objectpartners.rest.resource.RestResourceContext

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Transient

@Entity
@EntityWithRestResource
class Profile {
    @Id
    Long id

    String firstName

    String lastName

    @RestResourceMapper(
            external = true,
            context = RestResourceContext.LOCATOR,
            path = "/geocode/json",
            params = ["key=#apiKey", "components=postal_code:#id"],
            apiKey = RestResourceApiKey.GOOGLE,
            resolveToProperty = "location"
    )
    String zipCode

    @Transient
    Object location
}
