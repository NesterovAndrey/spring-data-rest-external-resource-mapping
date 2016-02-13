package com.objectpartners.rest.util

import com.objectpartners.domain.Profile
import com.objectpartners.domain.ProfileRepository
import com.objectpartners.domain.User
import com.objectpartners.domain.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BootstrapData {
    @Autowired
    UserRepository userRepository

    @Autowired
    ProfileRepository profileRepository

    void loadData() {
        User user = new User(
                id: 1L,
                username: "opi1",
                userProfile: "5"
        )

        userRepository.save(user)

        Profile profile = new Profile(
                id: Long.parseLong(user.userProfile),
                firstName: "Hammer",
                lastName: "Proper",
                zipCode: "55413"
        )

        profileRepository.save(profile)
    }
}
