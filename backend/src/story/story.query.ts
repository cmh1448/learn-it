import { Inject, Injectable, Logger } from '@nestjs/common';
import { Pageable, Pagination, offsetOf } from 'src/common/pagination';
import { User } from 'src/user/user.entity';
import { DataSource, FindManyOptions, Repository } from 'typeorm';
import { Story } from './story.entity';
import { InjectRepository } from '@nestjs/typeorm';
import { StoryResponse } from './story.dto';

@Injectable()
export class StoryQueryRepository {
  constructor(
    @InjectRepository(Story)
    readonly storyRepository: Repository<Story>
  ) {}

  async findMinePaginated(userId: number, pageable: Pageable): Promise<Pagination<StoryResponse>> {
    const query: FindManyOptions<Story> = {
      where: { owner: { id: userId } },
      take: pageable.pageSize,
      skip: offsetOf(pageable),
    };

    const [results, total] = await Promise.all([
      (await this.storyRepository.find(query)).map((story) => StoryResponse.from(story)),
      this.storyRepository.count(query),
    ]);

    return Pagination.of(await Promise.all(results), total, pageable);
  }
}
