package github.cmh1448.backend.domain.study.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import github.cmh1448.backend.domain.study.entity.Member
import github.cmh1448.backend.domain.study.entity.QMember.Companion.member
import github.cmh1448.backend.domain.user.dto.UserDto
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedModel
import org.springframework.stereotype.Repository

@Repository
class MemberQueryRepository (
    val queryFactory: JPAQueryFactory
) {
    fun findMemberByStudyAndUserId(studyId: Long, userEmail: String): Member? {
        return queryFactory.selectFrom(member)
            .where(member.study.id.eq(studyId).and(member.user.email.eq(userEmail)))
            .fetchFirst()
    }

    fun existsByStudyIdAndUserId(id: Long, email: String): Boolean {
        return queryFactory.selectFrom(member)
            .where(member.study.id.eq(id).and(member.user.email.eq(email)))
            .fetchFirst() != null
    }

    fun pageByStudyId(studyId: Long, pageable: Pageable): PagedModel<UserDto.Response> {
        val content = queryFactory.selectFrom(member)
            .where(member.study.id.eq(studyId))
            .leftJoin(member.user).fetchJoin()
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()
            .map { UserDto.Response(it.user) }

        val count = queryFactory.select(member.count()).from(member)
            .where(member.study.id.eq(studyId))
            .fetchFirst()

        return PagedModel(PageImpl(content, pageable, count ?: 0))
    }
}
