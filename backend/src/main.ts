import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { Logger, ValidationPipe } from '@nestjs/common';
import * as dotenv from 'dotenv';

dotenv.config({
  path: 'env/dev.env',
});

async function bootstrap() {
  const app = await NestFactory.create(AppModule);

  app.useGlobalPipes(new ValidationPipe());

  await app.listen(process.env.SERVER_PORT);
  Logger.log(`Now Server Is Running On ${process.env.SERVER_PORT}`);
}
bootstrap();
