package github.cmh1448.backend.domain.user.entity

import github.cmh1448.backend.domain.study.entity.Study
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

@Entity
@Table(name = "USERS")
class User (
    @Id
    val email: String,
    var username: String,
    var password: String,
    @ManyToMany(mappedBy = "members")
    val studies: MutableList<Study> = mutableListOf()
)