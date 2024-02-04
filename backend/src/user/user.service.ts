import { HttpException, Inject, Injectable, Logger } from '@nestjs/common';
import { User } from './user.entity';
import { RegisterRequest } from 'src/auth/auth.dto';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';

@Injectable()
export class UserService {
  constructor(
    @InjectRepository(User)
    private userRepository: Repository<User>
  ) {}

  async registerUser(request: RegisterRequest) {
    const exist = await this.userRepository.exist({
      where: {
        email: request.email,
      },
    });

    if (exist) throw new HttpException('이미 존재하는 유저입니다.', 400);

    const toSave = await User.from(request);
    const saved = await this.userRepository.save(toSave);

    return saved;
  }

  async findByEmail(email: string) {
    return await this.userRepository.findOneBy({ email });
  }
}
