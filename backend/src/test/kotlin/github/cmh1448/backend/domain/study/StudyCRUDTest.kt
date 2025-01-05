package github.cmh1448.backend.domain.study

import github.cmh1448.backend.domain.study.dto.StudyDto
import github.cmh1448.backend.domain.study.entity.enums.StudyType
import github.cmh1448.backend.domain.study.service.StudyService
import github.cmh1448.backend.domain.user.entity.User
import github.cmh1448.backend.domain.user.model.UserDetails
import github.cmh1448.backend.domain.user.repository.UserRepository
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

@SpringBootTest
@ActiveProfiles("local")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@DisplayName("스터디 CRUD 테스트")
class StudyCRUDTest  {
    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var studyService: StudyService

    private val user: User = User(
        email = "tester@test.com",
        username = "tester",
        password = "password"
    )

    @BeforeAll
    fun setUp() {
        userRepository.saveAndFlush(user)
    }

    @Test
    @DisplayName("스터디 생성")
    fun createStudy() {
        // given
        val request = StudyDto.CreateRequest(
            name = "test study",
            description = "test study description",
            type = StudyType.PRIVATE,
        )

        // when
        val response = studyService.createStudy(request, UserDetails(user))

        // then
        Assertions.assertThat(response.name).isEqualTo(request.name)
        Assertions.assertThat(response.description).isEqualTo(request.description)
        Assertions.assertThat(response.type).isEqualTo(request.type)
        Assertions.assertThat(response.master?.email).isEqualTo(user.email)
    }

    @Test
    @DisplayName("스터디 수정")
    fun updateStudy() {
        // given
        val request = StudyDto.CreateRequest(
            name = "test study",
            description = "test study description",
            type = StudyType.PRIVATE,
        )
        val createdStudy = studyService.createStudy(request, UserDetails(user))

        val updateRequest = StudyDto.UpdateRequest(
            name = "updated study",
            description = "updated study description",
            type = StudyType.PUBLIC
        )

        // when
        val updatedStudy = studyService.updateStudy(createdStudy.id!!, updateRequest)

        // then
        Assertions.assertThat(updatedStudy.name).isEqualTo(updateRequest.name)
        Assertions.assertThat(updatedStudy.description).isEqualTo(updateRequest.description)
        Assertions.assertThat(updatedStudy.type).isEqualTo(updateRequest.type)
    }

    @Test
    @DisplayName("공개 스터디를 비공개로 변경할 수 없음")
    fun cannotChangePublicStudyToPrivate() {
        // given
        val request = StudyDto.CreateRequest(
            name = "test study",
            description = "test study description",
            type = StudyType.PUBLIC,
        )
        val createdStudy = studyService.createStudy(request, UserDetails(user))

        val updateRequest = StudyDto.UpdateRequest(
            name = "updated study",
            description = "updated study description",
            type = StudyType.PRIVATE
        )

        // when
        val exception = Assertions.catchThrowable { studyService.updateStudy(createdStudy.id!!, updateRequest) }

        // then
        Assertions.assertThat(exception).isInstanceOf(RestException::class.java)
    }

    @Test
    @DisplayName("스터디 삭제")
    fun deleteStudy() {
        // given
        val request = StudyDto.CreateRequest(
            name = "test study",
            description = "test study description",
            type = StudyType.PRIVATE,
        )
        val createdStudy = studyService.createStudy(request, UserDetails(user))

        // when
        studyService.deleteStudy(createdStudy.id!!, UserDetails(user))

        // then
        val exception = Assertions.catchThrowable { studyService.deleteStudy(createdStudy.id!!, UserDetails(user)) }
        Assertions.assertThat(exception).isInstanceOf(RestException::class.java)
    }
}