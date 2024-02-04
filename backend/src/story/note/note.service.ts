import { Inject, Injectable, NotFoundException } from '@nestjs/common';
import { Repository } from 'typeorm';
import { Note } from './note.entity';
import { InjectRepository } from '@nestjs/typeorm';
import { Episode } from '../episode/episode.entity';
import { NoteCreateRequest, NoteResponse, NoteUpdateRequest } from './note.dto';
import { UserAccessToken } from 'src/auth/auth.service';
import { User } from 'src/user/user.entity';
import { Pageable } from 'src/common/pagination';
import { NoteQueryRepository } from './note.query';

@Injectable()
export class NoteService {
  constructor(
    @InjectRepository(Note)
    private noteRepository: Repository<Note>,
    @InjectRepository(Episode)
    private episodeRepository: Repository<Episode>,
    @InjectRepository(User)
    private userRepository: Repository<User>,
    private noteQueryRepository: NoteQueryRepository
  ) {}

  async createNote(request: NoteCreateRequest, episodeId: number, token: UserAccessToken) {
    const episode = await this.episodeRepository.findOneBy({ id: episodeId });
    (await episode.story).canUpdatedBy(token);

    const toSave = Note.from(request);
    toSave.episode = Promise.resolve(episode);
    toSave.writer = this.userRepository.findOneBy({ id: token.id });

    const saved = await this.noteRepository.save(toSave);
    return NoteResponse.from(saved);
  }

  async updateNote(request: NoteUpdateRequest, noteId: number, token: UserAccessToken) {
    const found = await this.noteRepository.findOneBy({ id: noteId });
    found.canUpdateBy(token);

    await this.noteRepository.update(noteId, request);

    return NoteResponse.from(found);
  }

  async paginateByEpisode(episodeId: number, pageable: Pageable) {
    return await this.noteQueryRepository.paginateByEpisode(episodeId, pageable);
  }

  async findById(id: number) {
    const found = await this.noteRepository.findOneBy({ id });

    return NoteResponse.from(found);
  }

  async deleteById(id: number, token: UserAccessToken) {
    const found = await this.noteRepository.findOneBy({ id });
    if (!found) throw new NotFoundException('노트를 찾을 수 없습니다.');

    found.canDeleteBy(token);

    await this.noteRepository.remove(found);
  }
}
