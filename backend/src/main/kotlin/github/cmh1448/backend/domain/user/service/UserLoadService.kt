package github.cmh1448.backend.domain.user.service

import github.cmh1448.backend.domain.user.model.UserDetails
import github.cmh1448.backend.domain.user.repository.UserRepository
import github.cmh1448.backend.system.security.service.UserLoadService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserLoadService(
    val userRepository: UserRepository
) : UserLoadService<UserDetails> {
    override fun loadUserByKey(key: String): Optional<UserDetails> {
        val user = userRepository.findById(key)

        return user.map {
            UserDetails(it)
        }
    }
}