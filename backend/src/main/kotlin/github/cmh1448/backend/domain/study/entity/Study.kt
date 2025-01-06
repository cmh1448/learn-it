package github.cmh1448.backend.domain.study.entity

import github.cmh1448.backend.domain.study.entity.enums.StudyType
import github.cmh1448.backend.domain.user.entity.User
import jakarta.persistence.*

@Entity
class Study (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var name: String,

    var description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    var master: User? = null,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "study", cascade = [CascadeType.ALL])
    val members: MutableList<Member> = mutableListOf(),

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "STUDY_BANNED_USER",
        joinColumns = [JoinColumn(name = "study_id")],
        inverseJoinColumns = [JoinColumn(name = "user_email")]
    )
    val bannedUsers: MutableList<User> = mutableListOf(),

    @Enumerated(EnumType.STRING)
    var type: StudyType,

    @OneToMany(mappedBy = "study", fetch = FetchType.LAZY)
    val subjects: MutableList<Subject> = mutableListOf(),
)