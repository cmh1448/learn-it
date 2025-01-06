package github.cmh1448.backend.domain.study.repository

import github.cmh1448.backend.domain.study.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
}