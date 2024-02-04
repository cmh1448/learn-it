import { RegisterRequest } from '../auth/auth.dto';
import { createHashedPassword } from '../common/securityUtils';
import { Column, Entity, PrimaryGeneratedColumn } from 'typeorm';

@Entity()
export class User {
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  nickname: string;

  @Column()
  name: string;

  @Column()
  email: string;

  @Column()
  password: string;

  @Column()
  salt: string;

  public static async from(req: RegisterRequest) {
    const result = new User();

    result.name = req.name;
    result.email = req.email;
    result.nickname = req.nickname;
    const { hash, salt } = await createHashedPassword(req.password);

    result.password = hash;
    result.salt = salt;

    return result;
  }
}
