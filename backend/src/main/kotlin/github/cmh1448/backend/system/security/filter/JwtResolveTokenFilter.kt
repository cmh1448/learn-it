package github.cmh1448.backend.system.security.filter

import github.cmh1448.backend.system.security.exception.JwtAuthenticationException
import github.cmh1448.backend.system.security.exception.JwtTokenMissingException
import github.cmh1448.backend.system.security.utility.JwtTokenResolver
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver


class JwtResolveTokenFilter (
    private val jwtTokenResolver: JwtTokenResolver,
    private val handlerExceptionResolver: HandlerExceptionResolver,
    private val ignorePatterns: List<String>,
    private val allowedPatterns: List<String>
) : OncePerRequestFilter() {
    private val antPathMatcher = AntPathMatcher()

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val servletPath = request.servletPath
        if (isMatchingURI(servletPath)) {
            try {
                val token = jwtTokenResolver.parseTokenFromRequest(request) ?: throw JwtTokenMissingException()
                val jwtToken = jwtTokenResolver.resolveTokenFromString(token)
                request.setAttribute("JwtToken", jwtToken)
                filterChain.doFilter(request, response)
            } catch (e: Exception) {
                if (e is JwtAuthenticationException) {
                    handlerExceptionResolver.resolveException(request, response, null, e)
                } else {
                    handlerExceptionResolver.resolveException(request, response, null, JwtAuthenticationException("Authentication failed", 401, e))
                }
            }
        } else {
            filterChain.doFilter(request, response)
        }
    }

    private fun isMatchingURI(servletPath: String): Boolean {
        return allowedPatterns.none { pattern -> antPathMatcher.match(pattern, servletPath) } &&
                ignorePatterns.any { pattern -> antPathMatcher.match(pattern, servletPath) }
    }
}
