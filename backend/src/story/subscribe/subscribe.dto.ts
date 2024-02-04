import { UserResponse } from 'src/user/user.dto';
import { StoryResponse } from '../story.dto';
import { SubscribeMember } from './subscribe.entity';

export class SubscribeResponse {
  story: StoryResponse;

  subscriber: UserResponse;

  subscriptionDate: Date;

  public static async from(subscribe: SubscribeMember): Promise<SubscribeResponse> {
    return Promise.resolve({
      story: await StoryResponse.from(await subscribe.story),
      subscriber: await UserResponse.from(await subscribe.subscriber),
      subscriptionDate: subscribe.subscriptionDate,
    });
  }
}

export class SubscribeRequest {
  storyId: number;
}
