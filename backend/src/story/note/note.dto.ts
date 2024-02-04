import { IsNotEmpty } from 'class-validator';
import { Note } from './note.entity';

export class NoteCreateRequest {
  @IsNotEmpty()
  title: string;

  @IsNotEmpty()
  content: string;
}

export class NoteUpdateRequest {
  title: string;

  content: string;
}

export class NoteResponse {
  id: number;

  episodeId: number;

  title: string;

  content: string;

  createdAt: Date;

  modifiedAt: Date;

  public static from(note: Note): NoteResponse {
    return {
      id: note.id,
      episodeId: note.episodeId,
      title: note.title,
      content: note.content,
      createdAt: note.createdAt,
      modifiedAt: note.modifiedAt,
    };
  }
}

export class NotePaginationResponse {
  id: number;

  episodeid: number;

  title: string;

  createdAt: Date;

  modifiedAt: Date;

  public static from(note: Note): NotePaginationResponse {
    return {
      id: note.id,
      episodeid: note.episodeId,
      title: note.title,
      createdAt: note.createdAt,
      modifiedAt: note.modifiedAt,
    };
  }
}
