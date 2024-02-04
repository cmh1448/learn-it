import { Body, Controller, Get, Param, Post, Query, UseGuards } from '@nestjs/common';
import { SubscribeService } from './subscribe.service';
import { Token } from 'src/auth/auth.decorator';
import { UserAccessToken } from 'src/auth/auth.service';
import { AuthGuard } from 'src/auth/auth.guard';

@Controller('api/stories/:storyId')
@UseGuards(AuthGuard)
export class SubscribeController {
  constructor(private subscribeService: SubscribeService) {}

  @Post('/subscribe')
  subscribe(@Param('storyId') storyId: number, @Token() token: UserAccessToken) {
    return this.subscribeService.subscribe(token.id, storyId);
  }

  @Post('/unsubscribe')
  unsubscribe(@Param('storyId') storyId: number, @Token() token: UserAccessToken) {
    this.subscribeService.unSubscribe(token, storyId);
  }
}

@Controller('api/subscribes/my')
@UseGuards(AuthGuard)
export class UserSubscribeController {
  constructor(private subscribeService: SubscribeService) {}

  @Get()
  findMines(@Token() token: UserAccessToken) {
    return this.subscribeService.findSubscriptionsByUser(token);
  }
}
