package github.cmh1448.backend.domain.study

import github.cmh1448.backend.domain.study.entity.Study
import github.cmh1448.backend.domain.study.entity.enums.StudyType
import github.cmh1448.backend.domain.study.repository.StudyRepository
import github.cmh1448.backend.domain.study.service.InvitationService
import github.cmh1448.backend.domain.user.entity.User
import github.cmh1448.backend.domain.user.model.UserDetails
import github.cmh1448.backend.domain.user.repository.UserRepository
import github.cmh1448.backend.system.exception.model.ErrorCode
import github.cmh1448.backend.system.exception.model.RestException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("local")
@DisplayName("스터디 초대 / 멤버 관리 테스트")
class StudyInvitationTest {

    @Autowired
    private lateinit var invitationService: InvitationService

    @Autowired
    private lateinit var studyRepository: StudyRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    private val user: User = User(
        email = "tester@test.com",
        username = "tester",
        password = "password"
    )
    private val user2: User = User(
        email = "tester2test.com",
        username = "tester2",
        password = "password"
    )
    private val study: Study = Study(
        name = "test study",
        description = "test study description",
        type = StudyType.PUBLIC,
        master = user
    )

    @BeforeAll
    fun setUp() {
        userRepository.saveAllAndFlush(listOf(user, user2))
        studyRepository.saveAndFlush(study)
    }

    @Test
    @DisplayName("공개 스터디 가입")
    @Transactional
    fun joinPublicStudy() {
        //when
        invitationService.join(study.id!!, UserDetails(user2))

        //then
        val updatedStudy = studyRepository.findById(study.id!!).get()
        Assertions.assertThat(updatedStudy.members).contains(user2)
    }

    @Test
    @DisplayName("비공개 스터디 초대 후 가입")
    @Transactional
    fun joinPrivateStudy() {
        //given
        val invitationToken = invitationService.createInvitationToken(study.id!!, LocalDate.now().plusDays(1), UserDetails(user))

        //when
        invitationService.processInvitation(invitationToken.token, UserDetails(user2))

        //then
        val updatedStudy = studyRepository.findById(study.id!!).get()

        Assertions.assertThat(updatedStudy.members).contains(user2)
    }

    @Test
    @DisplayName("만료된 초대 토큰 처리")
    @Transactional
    fun processExpiredInvitation() {
        //given
        val invitationToken = invitationService.createInvitationToken(study.id!!, LocalDate.now().minusDays(1), UserDetails(user))

        //when
        val exception = Assertions.catchThrowableOfType(RestException::class.java) {
            invitationService.processInvitation(
                invitationToken.token,
                UserDetails(user2)
            )
        }

        //then
        Assertions.assertThat(exception).isInstanceOf(RestException::class.java)
        Assertions.assertThat(exception.errorCode).isEqualTo(ErrorCode.STUDY_INVITATION_EXPIRED)
    }
}