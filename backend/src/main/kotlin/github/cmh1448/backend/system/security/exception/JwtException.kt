package github.cmh1448.backend.system.security.exception

open class JwtAuthenticationException(
    message: String,
    val status: Int,
    cause: Throwable? = null
) : RuntimeException(message, cause) {
    constructor(message: String, status: Int) : this(message, status, null)
}

class JwtInvalidTokenException(
    cause: Throwable? = null
) : JwtAuthenticationException("Invalid token", 401, cause) {
    constructor() : this(null)
}

class JwtParseException(
    cause: Throwable? = null
) : JwtAuthenticationException("Failed to parse token", 401, cause) {
    constructor() : this(null)
}

class JwtTokenExpiredException(
    cause: Throwable? = null
) : JwtAuthenticationException("Token has expired", 401, cause) {
    constructor() : this(null)
}

class JwtTokenMissingException(
    cause: Throwable? = null
) : JwtAuthenticationException("Token is missing", 401, cause) {
    constructor() : this(null)
}