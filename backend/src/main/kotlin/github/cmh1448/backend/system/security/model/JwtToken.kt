package github.cmh1448.backend.system.security.model

import java.time.LocalDateTime

enum class TokenType {
    ACCESS, REFRESH
}

class JwtToken (
    val type: TokenType,
    val subject: String,
    val expireAt: LocalDateTime
)