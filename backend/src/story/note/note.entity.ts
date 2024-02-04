import {
  Column,
  CreateDateColumn,
  Entity,
  ManyToOne,
  PrimaryGeneratedColumn,
  RelationId,
  UpdateDateColumn,
} from 'typeorm';
import { Episode } from '../episode/episode.entity';
import { NoteCreateRequest } from './note.dto';
import { User } from 'src/user/user.entity';
import { UserAccessToken } from 'src/auth/auth.service';
import { UnauthorizedException } from '@nestjs/common';

@Entity()
export class Note {
  @PrimaryGeneratedColumn()
  id: number;

  @ManyToOne((type) => Episode)
  episode: Promise<Episode>;

  @RelationId((note: Note) => note.episode)
  episodeId: number;

  title: string;

  content: string;

  @ManyToOne(() => User)
  writer: Promise<User>;

  writerId: number;

  @CreateDateColumn()
  createdAt: Date;

  @UpdateDateColumn()
  modifiedAt: Date;

  public static from(request: NoteCreateRequest): Note {
    const note = new Note();
    note.title = request.title;
    note.content = request.content;

    return note;
  }

  public canUpdateBy(token: UserAccessToken) {
    if (token.id === this.writerId) return;

    throw new UnauthorizedException();
  }

  public canDeleteBy(token: UserAccessToken) {
    if (token.id === this.writerId) return;

    throw new UnauthorizedException();
  }
}
