import { Module, forwardRef } from '@nestjs/common';
import { EpisodeService } from './episode.service';
import { EpisodeController } from './episode.controller';
import { StoryModule } from '../story.module';
import { UserModule } from 'src/user/user.module';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Episode } from './episode.entity';
import { Story } from '../story.entity';

@Module({
  imports: [TypeOrmModule.forFeature([Episode, Story]), forwardRef(() => StoryModule), UserModule],
  providers: [EpisodeService],
  controllers: [EpisodeController],
})
export class EpisodeModule {}
