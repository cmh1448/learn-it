package github.cmh1448.backend.domain.user.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "USERS")
class User (
    @Id
    val email: String,
    var username: String,
    var password: String,
)