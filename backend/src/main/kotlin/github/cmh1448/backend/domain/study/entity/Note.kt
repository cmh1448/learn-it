package github.cmh1448.backend.domain.study.entity

import jakarta.persistence.*


@Entity
class Note (
    @Id @GeneratedValue
    val id: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    var subject: Subject,

    var title: String,

    @Lob
    var content: String,
)