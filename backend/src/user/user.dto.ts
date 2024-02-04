import { User } from './user.entity';

export class UserResponse {
  id: number;

  nickname: string;

  name: string;

  email: string;

  public static from(user: User) {
    const result = new UserResponse();

    result.id = user.id;
    result.nickname = user.nickname;
    result.name = user.name;
    result.email = user.email;

    return result;
  }
}
