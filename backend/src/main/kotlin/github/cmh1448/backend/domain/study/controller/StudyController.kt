package github.cmh1448.backend.domain.study.controller

import github.cmh1448.backend.domain.study.dto.StudyDto
import github.cmh1448.backend.domain.study.service.InvitationService
import github.cmh1448.backend.domain.study.service.StudyService
import github.cmh1448.backend.domain.user.model.UserDetails
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/study")
class StudyController(
    private val studyService: StudyService,
    private val invitationService: InvitationService
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
        invitationService.join(studyId, user)
    }

    @PostMapping("/accept-invitation")
    fun acceptInvitation(
        request: StudyDto.AcceptInvitationRequest,
        @AuthenticationPrincipal
        user: UserDetails
    ) {
        invitationService.processInvitation(request.token, user)
    }
}