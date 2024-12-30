package github.cmh1448.backend.system.security.filter

import github.cmh1448.backend.system.security.exception.JwtAuthenticationException
import github.cmh1448.backend.system.security.exception.JwtInvalidTokenException
import github.cmh1448.backend.system.security.model.AuthDetails
import github.cmh1448.backend.system.security.model.JwtToken
import github.cmh1448.backend.system.security.service.UserLoadService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver

class JwtAuthenticationFilter<T : AuthDetails> (
    private val userLoadService: UserLoadService<T>,
    private val handlerExceptionResolver: HandlerExceptionResolver
) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val token = request.getAttribute("JwtToken") as JwtToken?

        if (token != null) {
            try {
                val userDetails = userLoadService.loadUserByKey(token.subject)
                if (userDetails.isEmpty) {
                    throw JwtInvalidTokenException()
                }

                SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(userDetails.get(), null, listOf(
                    SimpleGrantedAuthority("User")
                ))
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
}