import { HttpException, Injectable, Logger, UnauthorizedException } from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';
import { User } from 'src/user/user.entity';
import { UserService } from 'src/user/user.service';
import { RegisterRequest } from './auth.dto';
import { UserResponse } from 'src/user/user.dto';
import { createHashedPassword } from 'src/common/securityUtils';
import { DateTime } from 'luxon';

export interface UserAccessToken {
  id: number;

  nickname: string;

  name: string;

  email: string;
}

@Injectable()
export class AuthService {
  constructor(private userService: UserService, private jwtService: JwtService) {}

  async signup(req: RegisterRequest) {
    const registered = await this.userService.registerUser(req);

    return UserResponse.from(registered);
  }

  private async checkPassword(user: User, str: string) {
    const { hash } = await createHashedPassword(str, user.salt);

    return hash === user.password;
  }

  async signIn(email: string, pw: string) {
    const found = await this.userService.findByEmail(email);

    if (!found) throw new HttpException('사용자를 찾을 수 없습니다.', 404);

    if (!(await this.checkPassword(found, pw)))
      throw new UnauthorizedException('비밀번호가 일치하지 않습니다.');

    const expireDate = DateTime.now().plus({ days: 1 });

    const tokenSource: UserAccessToken = {
      id: found.id,
      name: found.name,
      nickname: found.nickname,
      email: found.email,
    };

    const jwtToken = await this.jwtService.signAsync(tokenSource, {
      secret: process.env.JWT_SECRET,
    });

    return {
      user: tokenSource,
      token: jwtToken,
      expire: expireDate.toISO(),
    };
  }
}
