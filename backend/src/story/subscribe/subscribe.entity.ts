import { User } from 'src/user/user.entity';
import { Column, Entity, ManyToOne, PrimaryColumn } from 'typeorm';
import { Story } from '../story.entity';

@Entity()
export class SubscribeMember {
  @PrimaryColumn()
  subscriberId: number;

  @PrimaryColumn()
  storyId: number;

  @ManyToOne(() => User)
  subscriber: Promise<User>;

  @ManyToOne(() => Story, (story) => story.subscribers)
  story: Promise<Story>;

  @Column()
  subscriptionDate: Date;
}
