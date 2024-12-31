package github.cmh1448.backend.domain.user.repository

import github.cmh1448.backend.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String> {
}