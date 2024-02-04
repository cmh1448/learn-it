import { HttpException, Injectable, Logger, NotFoundException } from '@nestjs/common';
import { StoryRequest, StoryResponse } from './story.dto';
import { Story } from './story.entity';
import { Repository } from 'typeorm';
import { UserAccessToken } from 'src/auth/auth.service';
import { StoryQueryRepository } from './story.query';
import { User } from 'src/user/user.entity';
import { Pageable } from 'src/common/pagination';
import { InjectRepository } from '@nestjs/typeorm';

@Injectable()
export class StoryService {
  constructor(
    @InjectRepository(Story)
    private storyRepository: Repository<Story>,
    @InjectRepository(User)
    private userRepository: Repository<User>,
    private storyQueryRepository: StoryQueryRepository
  ) {}

  async findById(id: number) {
    const found = await this.storyRepository.findOne({ where: { id }, relations: ['owner'] });
    if (!found) throw new NotFoundException('스토리를 찾을 수 없습니다.');

    return StoryResponse.from(found);
  }

  async deleteStory(id: number) {
    if (!(await this.storyRepository.exist({ where: { id } })))
      throw new NotFoundException('스토리를 찾을 수 없습니다.');

    await this.storyRepository.delete({ id });
  }

  async updateStory(request: StoryRequest, id: number, token: UserAccessToken) {
    const found = await this.storyRepository.findOne({ where: { id } });
    if (!found) throw new NotFoundException('스토리를 찾을 수 없습니다.');

    found.canUpdatedBy(token);

    await this.storyRepository.update({ id }, request);

    const updated = { ...found, ...request } as Story;
    return StoryResponse.from(updated);
  }

  async createStory(request: StoryRequest, token: UserAccessToken) {
    const user = await this.userRepository.findOneByOrFail({ id: token.id });
    const toSave = Story.of(request, user);

    const saved = await this.storyRepository.save(toSave);

    return await StoryResponse.from(saved);
  }

  async paginateMines(token: UserAccessToken, pageable: Pageable) {
    const results = await this.storyQueryRepository.findMinePaginated(token.id, pageable);

    return results;
  }
}
