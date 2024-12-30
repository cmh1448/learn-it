package github.cmh1448.backend.system.security.configurer

import github.cmh1448.backend.system.security.filter.JwtAuthenticationFilter
import github.cmh1448.backend.system.security.filter.JwtRefreshTokenFilter
import github.cmh1448.backend.system.security.filter.JwtResolveTokenFilter
import github.cmh1448.backend.system.security.model.AuthDetails
import github.cmh1448.backend.system.security.service.UserLoadService
import github.cmh1448.backend.system.security.utility.JwtTokenProvider
import github.cmh1448.backend.system.security.utility.JwtTokenResolver
import org.slf4j.LoggerFactory
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.HandlerExceptionResolver

class JwtAutoConfigurer <T : AuthDetails> (
    private val jwtTokenResolver: JwtTokenResolver,
    private val jwtTokenProvider: JwtTokenProvider,
    private val userLoadService: UserLoadService<T>,
    private val handlerExceptionResolver: HandlerExceptionResolver,
    private val accessTokenExpireHours: Long,
    private val pathPatternConfigurer: PathPatternConfigurer = PathPatternConfigurer()
) {
    private val logger = LoggerFactory.getLogger(JwtAutoConfigurer::class.java)

    fun pathConfigure(customizer: Customizer<PathPatternConfigurer>): JwtAutoConfigurer<T> {
        customizer.customize(this.pathPatternConfigurer)
        return this
    }

    fun configure(http: HttpSecurity) {
        val jwtAuthenticationFilter = JwtAuthenticationFilter(this.userLoadService, this.handlerExceptionResolver)
        val jwtRefreshTokenFilter = JwtRefreshTokenFilter(this.accessTokenExpireHours, this.jwtTokenProvider)
        val jwtResolveTokenFilter = JwtResolveTokenFilter(this.jwtTokenResolver, this.handlerExceptionResolver, this.pathPatternConfigurer.includePatternList, this.pathPatternConfigurer.excludePatternList)

        http
            .headers {
                it.frameOptions {
                    it.sameOrigin()
                }
            }
            .httpBasic {
                it.disable()
            }
            .csrf {
                it.disable()
            }
            .formLogin {
                it.disable()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

        http.addFilterBefore(jwtResolveTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
        http.addFilterAfter(jwtRefreshTokenFilter, JwtResolveTokenFilter::class.java)
        http.addFilterAfter(jwtAuthenticationFilter, JwtRefreshTokenFilter::class.java)

        logger.info("Jwt 인증 자동 설정이 완료되었습니다.")
    }

}
