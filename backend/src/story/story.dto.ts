import { UserResponse } from 'src/user/user.dto';
import { Story } from './story.entity';
import { IsNotEmpty } from 'class-validator';

export class StoryRequest {
  @IsNotEmpty()
  title: string;

  @IsNotEmpty()
  content: string;
}

export class StoryResponse {
  id: number;

  title: string;

  content: string;

  owner: UserResponse;

  createdAt: Date;

  modifiedAt: Date;

  public static async from(story: Story): Promise<StoryResponse> {
    const { id, title, content, createdAt, modifiedAt, owner } = story;

    return {
      id,
      title,
      content,
      createdAt,
      modifiedAt,
      owner: UserResponse.from(await owner),
    };
  }
}
