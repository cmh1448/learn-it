import { User } from './User';

export interface StoryPagination {
  id: number;

  title: string;

  owner: User;

  createdAt: string;

  modifiedAt: string;
}

export interface Story extends StoryPagination {
  content?: string;
}

export interface Episode {
  id: number;

  storyId: number;

  title: string;

  description: string;

  createdAt: string;

  modifiedAt: string;
}

export interface NotePagination {
  id: number;

  title: string;

  episode: Episode;

  createdAt: Date;

  modifiedAt: Date;
}

export interface Note extends NotePagination {
  content: string;
}
