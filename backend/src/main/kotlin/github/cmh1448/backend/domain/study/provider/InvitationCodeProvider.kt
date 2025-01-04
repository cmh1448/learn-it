package github.cmh1448.backend.domain.study.provider

import github.cmh1448.backend.domain.study.entity.Study
import github.cmh1448.backend.domain.user.model.UserDetails
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import java.security.Key
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@ConfigurationProperties(prefix = "service.invitation")
class InvitationProperties {
    var secret: String? = null
}

class InvitationToken (
    val studyId: Long,
    val expireAt: LocalDateTime,
    val issuerEmail: String
)

@Component
@EnableConfigurationProperties(InvitationProperties::class)
class InvitationTokenHandler(
    private final val invitationProperties: InvitationProperties
) {
    private final val secretKey: Key = invitationProperties.secret?.let {
        Keys.hmacShaKeyFor(it.toByteArray())
    } ?: Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun generateToken(study: Study, expireDate: LocalDate, user: UserDetails): String {
        val claims = Jwts.claims()
            .setSubject(study.id.toString())
            .setIssuer(user.key)

        val expireAt = LocalDateTime.of(expireDate, LocalDateTime.MIN.toLocalTime())

        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date.from(expireAt.atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(secretKey)
            .compact()
    }

    fun resolveToken(token: String): InvitationToken {
        val parsed = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)

        return InvitationToken(
            studyId = parsed.body.subject.toLong(),
            expireAt = parsed.body.expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
            issuerEmail = parsed.body.issuer
        )
    }
}