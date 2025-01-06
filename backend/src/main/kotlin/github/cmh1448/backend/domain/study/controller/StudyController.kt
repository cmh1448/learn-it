package github.cmh1448.backend.domain.study.controller

import github.cmh1448.backend.domain.study.dto.StudyDto
import github.cmh1448.backend.domain.study.service.InvitationService
import github.cmh1448.backend.domain.study.service.MemberService
import github.cmh1448.backend.domain.study.service.StudyService
import github.cmh1448.backend.domain.user.dto.UserDto
import github.cmh1448.backend.domain.user.model.UserDetails
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.data.web.PagedModel
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/study")
class StudyController(
    private val studyService: StudyService,
    private val invitationService: InvitationService,
    private val memberService: MemberService
) {
    @PostMapping
    fun createStudy(request: StudyDto.CreateRequest,
                    @AuthenticationPrincipal
                    user: UserDetails
                    ) : StudyDto.Response {
        return studyService.createStudy(request, user)
    }

    @PatchMapping("/{id}")
    fun updateStudy(
        @PathVariable
        id: Long,
        request: StudyDto.UpdateRequest,
        @AuthenticationPrincipal
        user: UserDetails
    ) : StudyDto.Response {
        return studyService.updateStudy(id, request)
    }

    @DeleteMapping("/{id}")
    fun deleteStudy(
        @PathVariable
        id: Long,
        @AuthenticationPrincipal
        user: UserDetails
    ) {
        studyService.deleteStudy(id, user)
    }

    @GetMapping
    fun paginateStudy(
        @PageableDefault
        page: Pageable,
        @AuthenticationPrincipal
        user: UserDetails
    ) : PagedModel<StudyDto.Response> {
        return studyService.paginateStudies(page, user)
    }


    @GetMapping("/{studyId}/invite-token")
    fun getInvitationToken(
        @PathVariable
        studyId: Long,
        @RequestParam
        expireDate: LocalDate,
        @AuthenticationPrincipal
        user: UserDetails
    ) : StudyDto.InvitationTokenResponse {
        return invitationService.createInvitationToken(studyId, expireDate, user)
    }

    @PostMapping("/{studyId}/join")
    fun joinStudy(
        @PathVariable
        studyId: Long,
        @AuthenticationPrincipal
        user: UserDetails
    ) {
        invitationService.joinToPublicStudy(studyId, user)
    }

    @PostMapping("/{studyId}/ban")
    fun banMember(
        @PathVariable
        studyId: Long,
        @RequestBody
        request: StudyDto.KickOrBanRequest,
        @AuthenticationPrincipal
        user: UserDetails
    ) {
        memberService.ban(studyId, request, user)
    }

    @PostMapping("/accept-invitation")
    fun acceptInvitation(
        request: StudyDto.AcceptInvitationRequest,
        @AuthenticationPrincipal
        user: UserDetails
    ) {
        invitationService.processInvitation(request.token, user)
    }

    @PostMapping("/{studyId}/leave")
    fun leaveStudy(
        @PathVariable
        studyId: Long,
        @AuthenticationPrincipal
        user: UserDetails
    ) {
        memberService.leave(studyId, user)
    }

    @PostMapping("/{studyId}/kick")
    fun kickMember(
        @PathVariable
        studyId: Long,
        @RequestBody
        request: StudyDto.KickOrBanRequest,
        @AuthenticationPrincipal
        user: UserDetails
    ) {
        memberService.kick(studyId, request, user)
    }

    @GetMapping("/{studyId}/members")
    fun paginateMembers(
        @PathVariable
        studyId: Long,
        @PageableDefault
        page: Pageable,
    ) : PagedModel<UserDto.Response> {
        return memberService.paginateMembersByStudyId(studyId, page)
    }
}