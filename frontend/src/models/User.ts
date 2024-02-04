export class User {
  id: number;

  nickname: string;

  name: string;

  email: string;

  profileImage: string;
}

export class LoginResult {
  user: User;

  token: string;

  expire: string;
}

export class SignUpRequest {
  email: string;

  nickname: string;

  password: string;

  name: string;
}
