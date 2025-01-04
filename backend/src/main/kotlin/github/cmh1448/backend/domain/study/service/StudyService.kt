package github.cmh1448.backend.domain.study.service

import github.cmh1448.backend.domain.study.dto.StudyDto
import github.cmh1448.backend.domain.study.entity.Study
import github.cmh1448.backend.domain.study.entity.enums.StudyType
import github.cmh1448.backend.domain.study.repository.StudyRepository
import github.cmh1448.backend.domain.user.model.UserDetails
import github.cmh1448.backend.domain.user.repository.UserRepository
import github.cmh1448.backend.system.exception.model.ErrorCode
import github.cmh1448.backend.system.exception.model.RestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StudyService (
    val studyRepository: StudyRepository,
    val  userRepository: UserRepository
) {
    @Transactional
    fun createStudy(request: StudyDto.CreateRequest, user: UserDetails) : StudyDto.Response {
        val toSave = request.toEntity()

        val master = userRepository.findById(user.email)
            .orElseThrow { throw RestException(ErrorCode.USER_NOT_FOUND) }
        toSave.master = master
        toSave.members.add(master)

        return StudyDto.Response(studyRepository.save(toSave))
    }

    @Transactional
    fun updateStudy(id: Long, request: StudyDto.UpdateRequest) : StudyDto.Response {
        val study = studyRepository.findById(id)
            .orElseThrow { throw RestException(ErrorCode.GLOBAL_NOT_FOUND) }

        request.name?.let { study.name = it }
        request.description?.let { study.description = it }


        request.type?.let {
            cannotChangeTypeOfPublicStudy(study)
            study.type = it
        }

        return StudyDto.Response(studyRepository.save(study))
    }

    @Transactional
    fun deleteStudy(id: Long, user: UserDetails) {
        val study = studyRepository.findById(id)
            .orElseThrow { throw RestException(ErrorCode.GLOBAL_NOT_FOUND) }

        if (study.master?.email != user.email) {
            throw RestException(ErrorCode.STUDY_ONLY_MASTER_CAN_DELETE)
        }

        studyRepository.delete(study)
    }

    private fun cannotChangeTypeOfPublicStudy(it: Study) {
        if (it.type == StudyType.PUBLIC) {
            throw RestException(ErrorCode.STUDY_TYPE_CHANGE_NOT_ALLOWED)
        }
    }
}