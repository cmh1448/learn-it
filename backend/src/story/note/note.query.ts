import { Inject, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Note } from './note.entity';
import { FindManyOptions, Repository } from 'typeorm';
import { Pageable, Pagination, offsetOf } from 'src/common/pagination';
import { NotePaginationResponse } from './note.dto';

@Injectable()
export class NoteQueryRepository {
  constructor(
    @InjectRepository(Note)
    private noteRepository: Repository<Note>
  ) {}

  async paginateByEpisode(episodeId: number, pageable: Pageable) {
    const query: FindManyOptions<Note> = {
      where: {
        episode: { id: episodeId },
      },
      take: pageable.pageSize,
      skip: offsetOf(pageable),
    };

    const [results, total] = await Promise.all([
      (await this.noteRepository.find(query)).map(note => NotePaginationResponse.from(note)),
      this.noteRepository.count(query),
    ]);

    return Pagination.of(results, total, pageable);
  }
}
