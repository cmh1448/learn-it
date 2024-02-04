import { HttpException, Inject, Injectable } from '@nestjs/common';
import { Repository } from 'typeorm';
import { Episode } from './episode.entity';
import { CreateEpisodeRequest, EpisodeResponse, UpdateEpisodeRequest } from './episode.dto';
import { Story } from '../story.entity';
import { UserAccessToken } from 'src/auth/auth.service';
import { InjectRepository } from '@nestjs/typeorm';

@Injectable()
export class EpisodeService {
  constructor(
    @InjectRepository(Episode)
    private episodeRepository: Repository<Episode>,
    @InjectRepository(Story)
    private storyRepository: Repository<Story>
  ) {}

  async createEpisode(request: CreateEpisodeRequest, storyId: number, token: UserAccessToken) {
    const toSave = Episode.from(request);

    const story = await this.storyRepository.findOneBy({ id: storyId });
    if (!story) throw new HttpException('스토리가 존재하지 않습니다.', 404);

    toSave.story = Promise.resolve(story);

    const saved = await this.episodeRepository.save(toSave);

    return EpisodeResponse.from(saved);
  }

  async findAllByStoryId(storyid: number) {
    const founds = await this.episodeRepository.findBy({ story: { id: storyid } });

    return founds.map((found) => EpisodeResponse.from(found));
  }

  async updateEpisode(request: UpdateEpisodeRequest, id: number, token: UserAccessToken) {
    const found = await this.episodeRepository.findOneBy({ id });
    const story = await found.story;
    story.canUpdatedBy(token);

    await this.episodeRepository.update({ id }, { ...request });

    const updated = { ...found, ...request } as Episode;
    return EpisodeResponse.from(updated);
  }

  async deleteEpisode(id: number, token: UserAccessToken) {
    const found = await this.episodeRepository.findOneBy({ id });
    const story = await found.story;

    story.canUpdatedBy(token);

    await this.episodeRepository.delete({ id });
  }
}
