import { IsEmail, IsNotEmpty } from 'class-validator';

export class LoginRequest {
  @IsNotEmpty()
  email: string;

  @IsNotEmpty()
  password: string;
}

export class RegisterRequest {
  @IsEmail()
  email: string;

  @IsNotEmpty()
  nickname: string;
  @IsNotEmpty()
  password: string;
  @IsNotEmpty()
  name: string;
}
