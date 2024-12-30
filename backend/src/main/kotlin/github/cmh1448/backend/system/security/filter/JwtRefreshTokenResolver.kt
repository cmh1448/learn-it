package github.cmh1448.backend.system.security.filter

import github.cmh1448.backend.system.security.model.JwtToken
import github.cmh1448.backend.system.security.model.TokenType
import github.cmh1448.backend.system.security.utility.JwtTokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter
import java.time.format.DateTimeFormatter

class JwtRefreshTokenResolver(
    private val accessTokenExpireHours: Long,
    private val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val token = request.getAttribute("JwtToken") as JwtToken?
        if (token != null && token.type == TokenType.REFRESH) {
            val refreshed = jwtTokenProvider.generateAccessToken(token, accessTokenExpireHours)

            response.setHeader("Refreshed-Access-Token", refreshed.tokenString)
            response.setHeader("Refreshed-Access-Token-Expire", refreshed.expireAt.format(DateTimeFormatter.ISO_DATE_TIME))

            filterChain.doFilter(request, response)
        } else {
            filterChain.doFilter(request, response)
        }
    }
}