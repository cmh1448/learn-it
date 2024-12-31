package github.cmh1448.backend.domain.user.controller

import github.cmh1448.backend.domain.user.dto.UserDto
import github.cmh1448.backend.domain.user.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController (
    val userService: UserService
) {
    @PostMapping("/login")
    fun login(
        @RequestBody
        request: UserDto.LoginRequest
    ) : UserDto.LoginResponse {
        return userService.login(request)
    }

    @PostMapping("/register")
    fun register(
        @RequestBody
        request: UserDto.RegisterRequest
    ) : UserDto.Response {
        return userService.register(request)
    }
}