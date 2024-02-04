import { Module } from '@nestjs/common';
import { SubscribeService } from './subscribe.service';
import { TypeOrmModule } from '@nestjs/typeorm';
import { SubscribeMember } from './subscribe.entity';
import { SubscribeController, UserSubscribeController } from './subscribe.controller';
import { User } from 'src/user/user.entity';
import { Story } from '../story.entity';
@Module({
  imports: [TypeOrmModule.forFeature([SubscribeMember, User, Story])],
  providers: [SubscribeService],
  controllers: [SubscribeController, UserSubscribeController],
})
export class SubscribeModule {}
