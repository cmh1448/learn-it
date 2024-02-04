import { Body, Controller, Delete, Get, Param, Patch, Post, UseGuards } from '@nestjs/common';
import { NoteService } from './note.service';
import { Pageable, PageableDefault } from 'src/common/pagination';
import { AuthGuard } from 'src/auth/auth.guard';
import { NoteCreateRequest, NoteUpdateRequest } from './note.dto';
import { Token } from 'src/auth/auth.decorator';
import { UserAccessToken } from 'src/auth/auth.service';

@Controller('/api/episodes/:episodeId/notes')
@UseGuards(AuthGuard)
export class NoteController {
  constructor(private noteService: NoteService) {}

  @Get()
  async paginateByEpisode(
    @Param('episodeId') episodeId: number,
    @PageableDefault() pageable: Pageable
  ) {
    return this.noteService.paginateByEpisode(episodeId, pageable);
  }

  @Post()
  async createNote(
    @Param('episodeId') episodeId: number,
    @Body() request: NoteCreateRequest,
    @Token() token: UserAccessToken
  ) {
    return this.noteService.createNote(request, episodeId, token);
  }

  @Patch('/:noteId')
  async updateNote(
    @Param('noteId') noteId: number,
    @Body() request: NoteUpdateRequest,
    @Token() token: UserAccessToken
  ) {
    return this.noteService.updateNote(request, noteId, token);
  }

  @Get('/:noteId')
  async findById(@Param('noteId') noteId: number) {
    return this.noteService.findById(noteId);
  }

  @Delete('/:noteId')
  async deleteNote(@Param('noteId') noteId: number, @Token() token: UserAccessToken) {
    return this.noteService.deleteById(noteId, token);
  }
}
