import { Inject, Module } from '@nestjs/common';
import { AppService } from './app.service';
import { AuthModule } from './auth/auth.module';
import { UserModule } from './user/user.module';
import { StoryModule } from './story/story.module';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { EpisodeModule } from './story/episode/episode.module';
import { TypeOrmModule } from '@nestjs/typeorm';


@Module({
  imports: [
    AuthModule,
    UserModule,
    TypeOrmModule.forRootAsync({
      useFactory: (config: ConfigService) => ({
        type: 'mariadb',
        host: config.get('DB_HOST'),
        port: Number(config.get('DB_PORT')),
        username: config.get('DB_USER'),
        password: config.get('DB_PASSWORD'),
        database: config.get('DB_DATABASE'),
        synchronize: true,
        autoLoadEntities: true,
      }),
      inject: [ConfigService],
    }),
    StoryModule,
    EpisodeModule,
    ConfigModule.forRoot({ isGlobal: true, envFilePath: './env/local.env' }),
  ],
  controllers: [],
  providers: [AppService],
})
export class AppModule {}
