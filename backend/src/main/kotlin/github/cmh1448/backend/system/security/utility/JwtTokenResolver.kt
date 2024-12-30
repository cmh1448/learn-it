package github.cmh1448.backend.system.security.utility

import github.cmh1448.backend.system.security.exception.JwtInvalidTokenException
import github.cmh1448.backend.system.security.exception.JwtParseException
import github.cmh1448.backend.system.security.exception.JwtTokenExpiredException
import github.cmh1448.backend.system.security.model.JwtToken
import github.cmh1448.backend.system.security.model.TokenType
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.http.HttpServletRequest
import java.security.Key
import java.time.ZoneId

class JwtTokenResolver(
    private val secret: Key
) {
    fun parseTokenFromRequest(request: HttpServletRequest): String? {
        return try {
            request.getHeader("Authorization") ?: null
        } catch (e: Exception) {
            null
        }.let {
            if (it != null && it.startsWith("Bearer ")) {
                it.substring(7)
            } else {
                null
            }
        }
    }

    fun resolveTokenFromString(tokenStr: String): JwtToken {
        return try {
            val parsed = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(tokenStr)
            JwtToken(
                type = TokenType.valueOf(parsed.body["type", String::class.java]),
                subject = parsed.body.subject,
                expireAt = parsed.body.expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
            )
        } catch (e: ExpiredJwtException) {
            throw JwtTokenExpiredException(e)
        } catch (e: SignatureException) {
            throw JwtInvalidTokenException(e)
        } catch (e: Exception) {
            throw JwtParseException(e)
        }
    }
}