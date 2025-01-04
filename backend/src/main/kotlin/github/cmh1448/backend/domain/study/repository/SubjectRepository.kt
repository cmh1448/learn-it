package github.cmh1448.backend.domain.study.repository

import github.cmh1448.backend.domain.study.entity.Subject
import org.springframework.data.jpa.repository.JpaRepository

interface SubjectRepository : JpaRepository<Subject, Long> {
}