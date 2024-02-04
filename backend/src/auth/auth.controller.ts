import { Body, Controller, HttpCode, HttpStatus, Logger, Post } from '@nestjs/common';
import { AuthService } from './auth.service';
import { LoginRequest, RegisterRequest } from './auth.dto';
import crypto from 'crypto';

@Controller('/api/auth')
export class AuthController {
  constructor(private authService: AuthService) {}

  @HttpCode(HttpStatus.OK)
  @Post('login')
  signIn(@Body() request: LoginRequest) {
    Logger.log(request);
    return this.authService.signIn(request.email, request.password);
  }

  @HttpCode(HttpStatus.OK)
  @Post('signup')
  signUp(@Body() request: RegisterRequest) {
    return this.authService.signup(request);
  }
}
