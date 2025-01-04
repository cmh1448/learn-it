package github.cmh1448.backend.domain.study.repository

import github.cmh1448.backend.domain.study.entity.Study
import org.springframework.data.jpa.repository.JpaRepository

interface StudyRepository : JpaRepository<Study, Long> {
}