package github.cmh1448.backend.domain.study.entity

import github.cmh1448.backend.domain.user.entity.User
import jakarta.persistence.*

@Entity
class Member (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    val study: Study,

    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,
)