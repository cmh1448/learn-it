package github.cmh1448.backend.domain.study.dto

import github.cmh1448.backend.domain.study.entity.Note

class NoteDto {
    class Response (
        val id: Long,
        val title: String,
        val content: String,
        val subjectId: Long
    ) {
        constructor(note: Note) : this(
            id = note.id,
            title = note.title,
            content = note.content,
            subjectId = note.subject.id
        )
    }
}