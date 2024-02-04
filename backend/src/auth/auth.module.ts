import { Module } from '@nestjs/common';
import { AuthService } from './auth.service';
import { AuthController } from './auth.controller';
import { JwtModule } from '@nestjs/jwt';
import { UserModule } from 'src/user/user.module';

@Module({
  imports: [JwtModule.register({
    global: true,
    secret: 'secret',
    signOptions: {
      expiresIn: '24h'
    }
  }), UserModule],
  providers: [AuthService],
  controllers: [AuthController]
})
export class AuthModule { }
