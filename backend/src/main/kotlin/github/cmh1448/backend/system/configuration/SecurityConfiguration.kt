package github.cmh1448.backend.system.configuration

import github.cmh1448.backend.domain.user.service.UserLoadService
import github.cmh1448.backend.system.security.configurer.JwtAutoConfigurerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfiguration (
    val jwtAutoConfigurerFactory: JwtAutoConfigurerFactory,
    val userLoadService: UserLoadService
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        jwtAutoConfigurerFactory.create(userLoadService)
            .pathConfigure {
                it.includeAll()
                it.excludePath("/api/user/login")
                it.excludePath("/api/user/register")
            }
            .configure(http)

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}