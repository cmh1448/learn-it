package github.cmh1448.backend.system.security.utility

import github.cmh1448.backend.system.security.model.AuthDetails
import github.cmh1448.backend.system.security.model.JwtDto
import github.cmh1448.backend.system.security.model.JwtToken
import io.jsonwebtoken.Jwts
import java.security.Key
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class JwtTokenProvider (
    private val secret: Key
) {
    fun generateAccessToken(user: AuthDetails, expireHours: Long): JwtDto.TokenData {
        val claims = Jwts.claims().setSubject(user.key)

        val expireLocalDateTime = LocalDateTime.now().plusHours(expireHours)

        val tokenString = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date())
            .setExpiration(Date.from(expireLocalDateTime.atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(secret)
            .claim("type", "access")
            .compact()

        return JwtDto.TokenData(
            tokenString = tokenString,
            expireAt = expireLocalDateTime
        )
    }

    fun generateAccessToken(expiredAccessToken: JwtToken, expireHours: Long): JwtDto.TokenData {
        val claims = Jwts.claims().setSubject(expiredAccessToken.subject)
        val expireLocalDateTime = LocalDateTime.now().plusHours(expireHours)

        val tokenString = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date())
            .setExpiration(Date.from(expireLocalDateTime.atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(secret)
            .claim("type", "access")
            .compact()

        return JwtDto.TokenData(
            tokenString = tokenString,
            expireAt = expireLocalDateTime
        )
    }

    fun generateRefreshToken(user: AuthDetails, expireHours: Long): JwtDto.TokenData {
        val claims = Jwts.claims().setSubject(user.key)
        val expireLocalDateTime = LocalDateTime.now().plusHours(expireHours)

        val tokenString = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date())
            .setExpiration(Date.from(expireLocalDateTime.atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(secret)
            .claim("type", "refresh")
            .compact()

        return JwtDto.TokenData(
            tokenString = tokenString,
            expireAt = expireLocalDateTime
        )
    }
}