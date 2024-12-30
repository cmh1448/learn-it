package github.cmh1448.backend.system.configuration

import github.cmh1448.backend.system.configuration.properties.JwtProperties
import github.cmh1448.backend.system.security.configurer.JwtAutoConfigurerFactory
import github.cmh1448.backend.system.security.utility.JwtTokenProvider
import github.cmh1448.backend.system.security.utility.JwtTokenResolver
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.util.StringUtils
import org.springframework.web.servlet.HandlerExceptionResolver
import java.security.Key

@EnableConfigurationProperties(JwtProperties::class)
@Configuration
class JwtConfiguration(jwtProperties: JwtProperties, handlerExceptionResolver: HandlerExceptionResolver) {
    private val logger = LoggerFactory.getLogger(JwtConfiguration::class.java)

    private final val secret: Key
    private final val accessTokenRefreshHours: Long
    private final val handlerExceptionResolver: HandlerExceptionResolver

    init {
        if (StringUtils.hasText(jwtProperties.secret) && jwtProperties.secret?.length!! < 32) {
            logger.error("JWT secret key must be at least 32 characters long")
            throw IllegalArgumentException("JWT secret key must be at least 32 characters long")
        }


        this.secret = if (StringUtils.hasText(jwtProperties.secret)) {
            Keys.hmacShaKeyFor(jwtProperties.secret?.toByteArray())
        } else {
            Keys.secretKeyFor(SignatureAlgorithm.HS256)
        }

        if (jwtProperties.accessTokenExpireHours == null || jwtProperties.accessTokenExpireHours <= 0) {
            logger.warn("JWT access token expiration time is not set or invalid. Defaulting to 24 hour")
            this.accessTokenRefreshHours = 24
        } else {
            this.accessTokenRefreshHours = jwtProperties.accessTokenExpireHours
        }

        this.handlerExceptionResolver = handlerExceptionResolver
    }

    @Bean
    fun jwtTokenResolver(): JwtTokenResolver {
        return JwtTokenResolver(secret)
    }

    @Bean
    fun jwtTokenProvider(): JwtTokenProvider {
        return JwtTokenProvider(secret)
    }

    @Bean
    fun jwtAutoConfigurerFactory(): JwtAutoConfigurerFactory {
        return JwtAutoConfigurerFactory(handlerExceptionResolver, jwtTokenResolver(), jwtTokenProvider(), accessTokenRefreshHours)
    }

    @Bean
    @ConditionalOnMissingBean
    fun dummyUserDetailsService(): UserDetailsService {
        return UserDetailsService {
            null //override loadUserByUsername() method
        }
    }
}