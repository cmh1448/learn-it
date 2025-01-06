package github.cmh1448.backend.domain.study.service

import github.cmh1448.backend.domain.study.dto.StudyDto
import github.cmh1448.backend.domain.study.entity.Study
import github.cmh1448.backend.domain.study.entity.enums.StudyType
import github.cmh1448.backend.domain.study.repository.MemberQueryRepository
import github.cmh1448.backend.domain.study.repository.MemberRepository
import github.cmh1448.backend.domain.study.repository.StudyRepository
import github.cmh1448.backend.domain.user.dto.UserDto
import github.cmh1448.backend.domain.user.model.UserDetails
import github.cmh1448.backend.system.exception.model.ErrorCode
import github.cmh1448.backend.system.exception.model.RestException
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService (
    private val studyRepository: StudyRepository,
    private val memberRepository: MemberRepository,
    private val memberQueryRepository: MemberQueryRepository
) {
    fun leave(studyId: Long, user: UserDetails) {
        if(studyRepository.existsById(studyId).not())
            throw RestException(ErrorCode.GLOBAL_NOT_FOUND)

        val member = memberQueryRepository.findMemberByStudyAndUserId(studyId, user.email)
            ?: throw RestException(ErrorCode.STUDY_NOT_MEMBER)

        memberRepository.delete(member)
    }

    fun ban(studyId: Long, request: StudyDto.KickOrBanRequest, user: UserDetails) {
        val study = studyRepository.findById(studyId)
            .orElseThrow { throw RestException(ErrorCode.GLOBAL_NOT_FOUND) }

        onlyPublicStudyCanBanUser(study)
        onlyMasterCanKickOrBanMember(study, user)

        val member = memberQueryRepository.findMemberByStudyAndUserId(studyId, request.memberEmail)
            ?: throw RestException(ErrorCode.STUDY_NOT_MEMBER)

        memberRepository.delete(member)
        study.bannedUsers.add(member.user)
    }


    fun kick(studyId: Long, request: StudyDto.KickOrBanRequest, user: UserDetails) {
        val study = studyRepository.findById(studyId)
            .orElseThrow { throw RestException(ErrorCode.GLOBAL_NOT_FOUND) }

        onlyMasterCanKickOrBanMember(study, user)

        val member = memberQueryRepository.findMemberByStudyAndUserId(studyId, request.memberEmail)
            ?: throw RestException(ErrorCode.STUDY_NOT_MEMBER)

        memberRepository.delete(member)
    }

    @Transactional(readOnly = true)
    fun paginateMembersByStudyId(studyId: Long, pageable: Pageable): PagedModel<UserDto.Response> {
        if(studyRepository.existsById(studyId).not())
            throw RestException(ErrorCode.GLOBAL_NOT_FOUND)

        return memberQueryRepository.pageByStudyId(studyId, pageable)
    }

    private fun onlyMasterCanKickOrBanMember(
        study: Study,
        user: UserDetails
    ) {
        if (study.master?.email != user.email)
            throw RestException(ErrorCode.STUDY_ONLY_MASTER_CAN_KICK)
    }

    private fun onlyPublicStudyCanBanUser(study: Study) {
        if (study.type != StudyType.PUBLIC)
            throw RestException(ErrorCode.STUDY_NOT_PUBLIC)
    }

}