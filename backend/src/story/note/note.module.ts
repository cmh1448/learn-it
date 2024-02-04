import { Module } from '@nestjs/common';
import { NoteService } from './note.service';
import { NoteController } from './note.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Note } from './note.entity';
import { Episode } from '../episode/episode.entity';
import { User } from 'src/user/user.entity';
import { NoteQueryRepository } from './note.query';

@Module({
  imports: [TypeOrmModule.forFeature([Note, Episode, User])],
  providers: [NoteService, NoteQueryRepository],
  controllers: [NoteController],
})
export class NoteModule {}
