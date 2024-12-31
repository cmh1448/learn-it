package github.cmh1448.backend.domain.user.dto

import github.cmh1448.backend.domain.user.entity.User
import github.cmh1448.backend.system.security.model.JwtDto

class UserDto {
    class Response (
        val email: String,
        val username: String,
    )

    class LoginRequest (
        val email: String,
        val password: String,
    )

    class LoginResponse (
        val accessToken: JwtDto.TokenData,
        val refreshToken: JwtDto.TokenData,
    )

    class RegisterRequest (
        val email: String,
        val username: String,
        val password: String,
    ) {
        fun toEntity(): User {
            return User(
                email = email,
                username = username,
                password = password,
            )
        }
    }
}