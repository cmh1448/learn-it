package github.cmh1448.backend.domain.study.repository

import github.cmh1448.backend.domain.study.entity.Note
import org.springframework.data.jpa.repository.JpaRepository

interface NoteRepository : JpaRepository<Note, Long> {
}