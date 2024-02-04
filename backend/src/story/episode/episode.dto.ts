import { IsNotEmpty } from 'class-validator';
import { Episode } from './episode.entity';

export class CreateEpisodeRequest {
  @IsNotEmpty()
  title: string;

  @IsNotEmpty()
  description: string;
}

export class UpdateEpisodeRequest {
  title: string;

  description: string;
}

export class EpisodeResponse {
  id: number;

  storyId: number;

  title: string;

  description: string;

  createdAt: Date;

  modifiedAt: Date;

  public static from(episode: Episode): EpisodeResponse {
    return {
      id: episode.id,
      storyId: episode.storyId,
      title: episode.title,
      description: episode.description,
      createdAt: episode.createdAt,
      modifiedAt: episode.modifiedAt,
    };
  }
}
