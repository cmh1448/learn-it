import {
  Body,
  Controller,
  Get,
  Logger,
  Param,
  Patch,
  Post,
  Query,
  Request,
  UseGuards,
} from '@nestjs/common';
import { Pageable, PageableDefault } from 'src/common/pagination';
import { StoryService } from './story.service';
import { UserAccessToken } from 'src/auth/auth.service';
import { StoryRequest } from './story.dto';
import { Token } from 'src/auth/auth.decorator';
import { AuthGuard } from 'src/auth/auth.guard';

@Controller('/api/stories')
@UseGuards(AuthGuard)
export class StoryController {
  constructor(private storyService: StoryService) {}

  @Get('/my')
  async findMines(@PageableDefault() pageable: Pageable, @Token() token: UserAccessToken) {
    return await this.storyService.paginateMines(token, pageable);
  }

  @Post()
  async createStory(@Body() request: StoryRequest, @Token() token: UserAccessToken) {
    return await this.storyService.createStory(request, token);
  }

  @Get('/:id')
  async findById(@Param('id') id: number) {
    return await this.storyService.findById(id);
  }

  @Patch('/:id')
  async updateById(
    @Param('id') id: number,
    @Token() token: UserAccessToken,
    @Body() updateRequest: StoryRequest
  ) {
    return await this.storyService.updateStory(updateRequest, id, token);
  }
}
