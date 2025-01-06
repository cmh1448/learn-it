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

    fun joinToPublicStudy(studyId: Long, user: UserDetails) {
        val study = studyRepository.findById(studyId)
            .orElseThrow { throw RestException(ErrorCode.GLOBAL_NOT_FOUND) }

        cannotJoinToNonPublicStudy(study)
        cannotJoinIfBanned(study, user)

        study.members.add(user.entity)
    }

    private fun cannotJoinIfBanned(
        study: Study,
        user: UserDetails
    ) {
        if (study.bannedUsers.find { it.email == user.email } != null)
            throw RestException(ErrorCode.STUDY_BANNED_USER)
    }

    fun leave(studyId: Long, user: UserDetails) {
        val study = studyRepository.findById(studyId)
            .orElseThrow { throw RestException(ErrorCode.GLOBAL_NOT_FOUND) }

        onlyMemberCanLeaveStudy(study, user)

        study.members.remove(user.entity)
    }

    fun ban(studyId: Long, request: StudyDto.KickOrBanRequest, user: UserDetails) {
        val study = studyRepository.findById(studyId)
            .orElseThrow { throw RestException(ErrorCode.GLOBAL_NOT_FOUND) }

        onlyPublicStudyCanBanUser(study)
        onlyMasterCanKickOrBanMember(study, user)

        val member = study.members.find { it.email == request.memberEmail }
            ?: throw RestException(ErrorCode.STUDY_NOT_MEMBER)

        study.members.remove(member)
    }

    private fun onlyMemberCanLeaveStudy(
        study: Study,
        user: UserDetails
    ) {
        (study.members.indexOf(user.entity)
            .takeIf { it != -1 }
            ?: throw RestException(ErrorCode.STUDY_NOT_MEMBER))
    }

    fun kick(studyId: Long, request: StudyDto.KickOrBanRequest, user: UserDetails) {
        val study = studyRepository.findById(studyId)
            .orElseThrow { throw RestException(ErrorCode.GLOBAL_NOT_FOUND) }

        val member = study.members.find { it.email == request.memberEmail }
            ?: throw RestException(ErrorCode.STUDY_NOT_MEMBER)

        onlyMasterCanKickOrBanMember(study, user)

        study.members.remove(member)
    }

    private fun onlyMasterCanKickOrBanMember(
        study: Study,
        user: UserDetails
    ) {
        if (study.master?.email != user.email)
            throw RestException(ErrorCode.STUDY_ONLY_MASTER_CAN_KICK)
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

        cannotJoinTwice(study, user)

        study.members.add(user.entity)
    }

    private fun cannotJoinTwice(
        study: Study,
        user: UserDetails
    ) {
        if (study.members.find { it.email == user.email } != null)
            throw RestException(ErrorCode.STUDY_ALREADY_MEMBER)
    }

    private fun onlyMasterCanInvite(
        foundStudy: Study,
        user: UserDetails
    ) {
        if (foundStudy.master?.email != user.email)
            throw RestException(ErrorCode.STUDY_ONLY_MASTER_CAN_INVITE)
    }


    private fun cannotJoinToNonPublicStudy(study: Study) {
        if (study.type == StudyType.PUBLIC)
            throw RestException(ErrorCode.STUDY_NOT_PUBLIC)
    }

    private fun onlyPublicStudyCanBanUser(study: Study) {
        if (study.type != StudyType.PUBLIC)
            throw RestException(ErrorCode.STUDY_NOT_PUBLIC)
    }
}