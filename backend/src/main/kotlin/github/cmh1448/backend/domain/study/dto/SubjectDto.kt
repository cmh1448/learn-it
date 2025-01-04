package github.cmh1448.backend.domain.study.dto

import github.cmh1448.backend.domain.study.entity.Subject

class SubjectDto {
    class Response (
        val id: Long,
        val name: String,
        val description: String,
        val order: Int
    ) {
        constructor(subject: Subject) : this(
            id = subject.id,
            name = subject.name,
            description = subject.description,
            order = subject.order
        )
    }
}