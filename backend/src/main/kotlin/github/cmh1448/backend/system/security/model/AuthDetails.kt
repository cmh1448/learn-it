package github.cmh1448.backend.system.security.model

import org.springframework.security.core.AuthenticatedPrincipal

abstract class AuthDetails : AuthenticatedPrincipal {
    abstract val key: String

    override fun getName(): String {
        return this.key
    }
}