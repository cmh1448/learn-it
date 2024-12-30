package github.cmh1448.backend.system.security.model

import java.time.LocalDateTime

class JwtDto {
    class TokenData (
        val tokenString: String,
        val expireAt: LocalDateTime
    ){
    }
}