package github.cmh1448.backend.domain.user.entity

import github.cmh1448.backend.domain.study.entity.Member
import jakarta.persistence.*

@Entity
@Table(name = "USERS")
class User (
    @Id
    val email: String,
    var username: String,
    var password: String,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    val members: MutableList<Member> = mutableListOf(),
)