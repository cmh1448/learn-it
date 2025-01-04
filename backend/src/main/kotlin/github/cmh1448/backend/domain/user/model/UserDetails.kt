package github.cmh1448.backend.domain.user.model

import github.cmh1448.backend.domain.user.entity.User
import github.cmh1448.backend.system.security.model.AuthDetails

class UserDetails(
    val email: String,
    val username: String,
    val entity: User
) : AuthDetails() {
    constructor(user: User) : this(
        user.email,
        user.username,
        user
    )

    override val key: String
        get() = email
}