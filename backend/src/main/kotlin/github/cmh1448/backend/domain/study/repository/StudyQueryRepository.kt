package github.cmh1448.backend.domain.study.repository

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import github.cmh1448.backend.domain.study.dto.StudyDto
import github.cmh1448.backend.domain.study.entity.QStudy.Companion.study
import github.cmh1448.backend.domain.study.entity.enums.StudyType
import github.cmh1448.backend.domain.user.model.UserDetails
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedModel
import org.springframework.stereotype.Repository

@Repository
class StudyQueryRepository(
    val queryFactory: JPAQueryFactory,
){

    fun paginateStudy(pageable: Pageable, user: UserDetails): PagedModel<StudyDto.Response> {
        val content = queryFactory.selectFrom(study)
            .where(
                pagingConditionByUser(user)
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()
            .map { StudyDto.Response(it) }

        val count = queryFactory.select(study.count()).from(study)
            .fetchFirst()

        return PagedModel(PageImpl(content, pageable, count ?: 0))
    }

    private fun pagingConditionByUser(user: UserDetails): BooleanExpression =
        // 공개 스터디이거나 사용자가 멤버인 스터디만 조회
        study.type.eq(StudyType.PUBLIC).or(study.members.any().user.email.eq(user.email))
}