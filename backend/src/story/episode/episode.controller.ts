import { Body, Controller, Delete, Get, Param, Patch, Post, UseGuards } from '@nestjs/common';
import { EpisodeService } from './episode.service';
import { CreateEpisodeRequest, UpdateEpisodeRequest } from './episode.dto';
import { AuthGuard } from 'src/auth/auth.guard';
import { Token } from 'src/auth/auth.decorator';
import { UserAccessToken } from 'src/auth/auth.service';

@Controller()
@UseGuards(AuthGuard)
export class EpisodeController {
  constructor(private episodeService: EpisodeService) {}

  @Get('/api/stories/:storyId/episodes')
  async findAllByStory(@Param('storyId') storyId: number) {
    return await this.episodeService.findAllByStoryId(storyId);
  }

  @Post('/api/stories/:storyId/episodes')
  async createEpisode(
    @Body() request: CreateEpisodeRequest,
    @Param('storyId') storyId: number,
    @Token() token: UserAccessToken
  ) {
    return this.episodeService.createEpisode(request, storyId, token);
  }

  @Patch('/api/episodes/:episodeId')
  async updateEpisode(
    @Body() request: UpdateEpisodeRequest,
    @Param('episodeId') episodeid: number,
    @Token() token: UserAccessToken
  ) {
    return this.episodeService.updateEpisode(request, episodeid, token);
  }

  @Delete('/api/episodes/:episodeid')
  async deleteEpisode(@Param('episodeId') episodeId: number, @Token() token: UserAccessToken) {
    return this.episodeService.deleteEpisode(episodeId, token);
  }
}
