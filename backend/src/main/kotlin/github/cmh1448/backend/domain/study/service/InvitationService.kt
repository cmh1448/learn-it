package github.cmh1448.backend.domain.study.service

import github.cmh1448.backend.domain.study.dto.StudyDto
import github.cmh1448.backend.domain.study.entity.Study
import github.cmh1448.backend.domain.study.entity.enums.StudyType
import github.cmh1448.backend.domain.study.provider.InvitationToken
import github.cmh1448.backend.domain.study.provider.InvitationTokenHandler
import github.cmh1448.backend.domain.study.repository.StudyRepository
import github.cmh1448.backend.domain.user.model.UserDetails
import github.cmh1448.backend.system.exception.model.ErrorCode
import github.cmh1448.backend.system.exception.model.RestException
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class InvitationService(
    val studyRepository: StudyRepository,
    private val invitationTokenHandler: InvitationTokenHandler,
) {

    fun join(studyId: Long, user: UserDetails) {
        val study = studyRepository.findById(studyId)
            .orElseThrow { throw RestException(ErrorCode.GLOBAL_NOT_FOUND) }

        cannotJoinToNonPublicStudy(study)

        study.members.add(user.entity)
    }

    fun createInvitationToken(studyId: Long, expireDate: LocalDate, user: UserDetails) : StudyDto.InvitationTokenResponse {
        val foundStudy = studyRepository.findById(studyId)
            .orElseThrow { throw RestException(ErrorCode.GLOBAL_NOT_FOUND) }

        onlyMasterCanInvite(foundStudy, user)

        val token = invitationTokenHandler.generateToken(foundStudy, expireDate, user)

        return StudyDto.InvitationTokenResponse(token)
    }

    fun processInvitation(token: String, user: UserDetails) {
        val tokenData: InvitationToken?

        try {
            tokenData = invitationTokenHandler.resolveToken(token)
        } catch (e: ExpiredJwtException) {
            throw RestException(ErrorCode.STUDY_INVITATION_EXPIRED)
        }

        val study = studyRepository.findById(tokenData.studyId)
            .orElseThrow { throw RestException(ErrorCode.GLOBAL_NOT_FOUND) }

        study.members.add(user.entity)
    }

    private fun onlyMasterCanInvite(
        foundStudy: Study,
        user: UserDetails
    ) {
        if (foundStudy.master?.email != user.email)
            throw RestException(ErrorCode.STUDY_ONLY_MASTER_CAN_INVITE)
    }


    private fun cannotJoinToNonPublicStudy(study: Study) {
        if (study.type != StudyType.PUBLIC)
            throw RestException(ErrorCode.STUDY_NOT_PUBLIC)
    }
}