package github.cmh1448.backend.domain.user.service

import github.cmh1448.backend.domain.user.dto.UserDto
import github.cmh1448.backend.domain.user.model.UserDetails
import github.cmh1448.backend.domain.user.repository.UserRepository
import github.cmh1448.backend.system.exception.model.ErrorCode
import github.cmh1448.backend.system.exception.model.RestException
import github.cmh1448.backend.system.security.utility.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService (
    val jwtTokenProvider: JwtTokenProvider,
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder
) {
    fun login(request: UserDto.LoginRequest): UserDto.LoginResponse {
        val user = userRepository.findById(request.email)
            .orElseThrow { RestException(ErrorCode.USER_NOT_FOUND) }

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw RestException(ErrorCode.USER_PASSWORD_MISMATCH)
        }

        val userDetails = UserDetails(user)

        val refreshToken = jwtTokenProvider.generateRefreshToken(userDetails, 24)
        val accessToken = jwtTokenProvider.generateAccessToken(userDetails, 1)

        return UserDto.LoginResponse(accessToken, refreshToken)
    }

    fun register(request: UserDto.RegisterRequest): UserDto.Response {
        if (userRepository.existsById(request.email)) {
            throw RestException(ErrorCode.GLOBAL_ALREADY_EXIST)
        }

        val user = request.toEntity()
        user.password = passwordEncoder.encode(user.password)

        userRepository.save(user)

        return UserDto.Response(user.email, user.username)
    }
}