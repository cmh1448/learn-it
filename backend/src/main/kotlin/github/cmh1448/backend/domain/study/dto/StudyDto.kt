package github.cmh1448.backend.domain.study.dto

import github.cmh1448.backend.domain.study.entity.Study
import github.cmh1448.backend.domain.study.entity.enums.StudyType
import github.cmh1448.backend.domain.user.dto.UserDto

class StudyDto {
    class CreateRequest (
        val name: String,
        val description: String,
        val masterId: String,
        val type: StudyType
    ) {
        fun toEntity() = Study(
            name = name,
            description = description,
            type = type,
        )
    }

    class UpdateRequest (
        val name: String?,
        val description: String?,
        val type: StudyType?
    )

    class AcceptInvitationRequest (
        val token: String
    )

    class InvitationTokenResponse (
        val token: String
    )

    class Response (
        val id: Long?,
        val name: String,
        val description: String,
        val master: UserDto.Response?,
        val type: StudyType
    ) {
        constructor(study: Study) : this(
            id = study.id,
            name = study.name,
            description = study.description,
            master = study.master?.let { UserDto.Response(it) },
            type = study.type
        )
    }

    class DetailResponse (
        val id: Long?,
        val name: String,
        val description: String,
        val master: UserDto.Response?,
        val type: StudyType,
        val subjects: List<SubjectDto.Response>
    ) {
        constructor(study: Study) : this(
            id = study.id,
            name = study.name,
            description = study.description,
            master = study.master?.let {  UserDto.Response(it) },
            type = study.type,
            subjects = study.subjects.map { SubjectDto.Response(it) }
        )
    }
}