package github.cmh1448.backend.system.security.configurer

import github.cmh1448.backend.system.security.model.AuthDetails
import github.cmh1448.backend.system.security.service.UserLoadService
import github.cmh1448.backend.system.security.utility.JwtTokenProvider
import github.cmh1448.backend.system.security.utility.JwtTokenResolver
import org.springframework.web.servlet.HandlerExceptionResolver


class JwtAutoConfigurerFactory(
    private val handlerExceptionResolver: HandlerExceptionResolver,
    private val jwtTokenResolver: JwtTokenResolver,
    private val jwtTokenProvider: JwtTokenProvider,
    private val accessTokenExpireHours: Long
) {
    fun <T : AuthDetails> create(userLoadService: UserLoadService<T>): JwtAutoConfigurer<T> {
        return JwtAutoConfigurer(jwtTokenResolver, jwtTokenProvider, userLoadService, handlerExceptionResolver, accessTokenExpireHours)
    }
}