import { Module, forwardRef } from '@nestjs/common';
import { StoryService } from './story.service';
import { NoteModule } from './note/note.module';
import { StoryController } from './story.controller';
import { StoryQueryRepository } from './story.query';
import { UserModule } from 'src/user/user.module';
import { EpisodeModule } from './episode/episode.module';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Story } from './story.entity';
import { User } from 'src/user/user.entity';
import { SubscribeModule } from './subscribe/subscribe.module';

@Module({
  providers: [StoryService, StoryQueryRepository],
  imports: [
    NoteModule,
    UserModule,
    TypeOrmModule.forFeature([Story, User]),
    forwardRef(() => EpisodeModule),
    SubscribeModule,
  ],
  controllers: [StoryController],
  exports: [StoryService],
})
export class StoryModule {}
