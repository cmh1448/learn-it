import {
  Entity,
  PrimaryGeneratedColumn,
  ManyToOne,
  Column,
  CreateDateColumn,
  UpdateDateColumn,
  RelationId,
  JoinColumn,
} from 'typeorm';
import { Story } from '../story.entity';
import { CreateEpisodeRequest } from './episode.dto';

@Entity()
export class Episode {
  @PrimaryGeneratedColumn()
  id: number;

  @ManyToOne((type) => Story)
  @JoinColumn()
  story: Promise<Story>;

  @RelationId((episode: Episode) => episode.story)
  storyId: number;

  @Column()
  title: string;

  @Column()
  description: string;

  @CreateDateColumn()
  createdAt: Date;

  @UpdateDateColumn()
  modifiedAt: Date;

  public static from(request: CreateEpisodeRequest) {
    const result = new Episode();
    result.title = request.title;
    result.description = request.description;

    return result;
  }
}
