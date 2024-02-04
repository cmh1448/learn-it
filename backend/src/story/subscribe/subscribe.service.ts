import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { User } from 'src/user/user.entity';
import { Story } from '../story.entity';
import { SubscribeResponse } from './subscribe.dto';
import { SubscribeMember } from './subscribe.entity';
import { UserAccessToken } from 'src/auth/auth.service';

@Injectable()
export class SubscribeService {
  constructor(
    @InjectRepository(SubscribeMember)
    private subscribeMemberRepository: Repository<SubscribeMember>,
    @InjectRepository(Story)
    private storyRepository: Repository<Story>,
    @InjectRepository(User)
    private userRepository: Repository<User>
  ) {}

  async subscribe(userId: number, storyId: number) {
    const toSave = new SubscribeMember();

    const foundStory = await this.storyRepository.findOneBy({ id: storyId });
    if (!foundStory) throw new NotFoundException();
    const foundUser = await this.userRepository.findOneBy({ id: userId });
    if (!foundUser) throw new NotFoundException();

    toSave.story = Promise.resolve(foundStory);
    toSave.subscriber = Promise.resolve(foundUser);

    const saved = await this.subscribeMemberRepository.save(toSave);

    return SubscribeResponse.from(saved);
  }

  async unSubscribe(owner: UserAccessToken, storyId: number) {
    const found = await this.subscribeMemberRepository.findOneBy({
      subscriber: { id: owner.id },
      story: { id: storyId },
    });

    if (!found) throw new NotFoundException();

    await this.subscribeMemberRepository.delete({
      subscriber: { id: owner.id },
      story: { id: storyId },
    });
  }

  async findSubscribersByStory(storyId: number) {
    const founds = await this.subscribeMemberRepository.find({
      where: { story: { id: storyId } },
      relations: ['story', 'subscriber'],
    });

    return founds.map((it) => SubscribeResponse.from(it));
  }

  async findSubscriptionsByUser(token: UserAccessToken): Promise<SubscribeResponse[]> {
    const founds = await this.subscribeMemberRepository.find({
      where: {
        subscriber: {
          id: token.id,
        },
      },
      relations: {
        story: true,
      },
    });

    return Promise.all(founds.map((it) => SubscribeResponse.from(it)));
  }
}
