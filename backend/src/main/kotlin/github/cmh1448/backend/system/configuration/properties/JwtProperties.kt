package github.cmh1448.backend.system.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
class JwtProperties (
    val secret: String?,
    val accessTokenExpireHours: Long?,
) {
}