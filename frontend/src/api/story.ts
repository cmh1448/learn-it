import { Episode, Story, StoryPagination } from '@/models/Story';
import axios from './axios';
import { Pageable, Pagination } from '@/models/Pagination';

export const fetchMyStories = async (pageable: Pageable = {}) => {
  const result = await axios.get('/api/stories/my', {
    params: pageable,
  });

  return result.data as Pagination<StoryPagination>;
};

export const fetchStoryById = async (id: number) => {
  const result = await axios.get(`/api/stories/${id}`);

  return result.data as Story;
};

export const fetchEpisodesByStoryId = async (storyId: number) => {
  const result = await axios.get(`/api/stories/${storyId}/episodes`);

  return result.data as Episode[];
};
