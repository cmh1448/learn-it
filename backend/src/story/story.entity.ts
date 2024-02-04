import { User } from '../user/user.entity';
import {
  Column,
  CreateDateColumn,
  Entity,
  ManyToOne,
  OneToMany,
  PrimaryGeneratedColumn,
  RelationId,
  UpdateDateColumn,
} from 'typeorm';
import { StoryRequest } from './story.dto';
import { UserAccessToken } from '../auth/auth.service';
import { UnauthorizedException } from '@nestjs/common';
import { SubscribeMember } from './subscribe/subscribe.entity';

@Entity()
export class Story {
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  title: string;

  @Column({
    length: 2000,
  })
  content: string;

  @ManyToOne((type) => User)
  owner: Promise<User>;
  ownerId: number;

  @OneToMany(() => SubscribeMember, (member) => member.story)
  subscribers: Promise<SubscribeMember[]>;

  @CreateDateColumn()
  createdAt: Date;

  @UpdateDateColumn()
  modifiedAt: Date;

  public static of(request: StoryRequest, user: User) {
    const result = new Story();

    result.title = request.title;
    result.owner = Promise.resolve(user);
    result.content = request.content;

    return result;
  }

  public canUpdatedBy(token: UserAccessToken) {
    if (this.ownerId === token.id) return;

    throw new UnauthorizedException();
  }

  public canDeleteBy(token: UserAccessToken) {
    if (this.ownerId === token.id) return;

    throw new UnauthorizedException();
  }
}
