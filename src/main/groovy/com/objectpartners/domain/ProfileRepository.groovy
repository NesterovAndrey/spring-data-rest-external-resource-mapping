package com.objectpartners.domain

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "profiles", path = "profiles")
interface ProfileRepository extends PagingAndSortingRepository<Profile, Long> {}
