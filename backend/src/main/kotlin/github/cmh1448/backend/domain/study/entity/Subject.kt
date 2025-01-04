package github.cmh1448.backend.domain.study.entity

import jakarta.persistence.*

@Entity
class Subject (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToOne(fetch = FetchType.LAZY)
    var study: Study,
    var name: String,
    var description: String,
    @Column(name = "order_num")
    @OrderBy
    var order: Int
)