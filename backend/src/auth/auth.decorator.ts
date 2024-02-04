import { ExecutionContext, createParamDecorator } from '@nestjs/common';
import { Request } from 'express';

export const Token = createParamDecorator((data: unknown, ctx: ExecutionContext) => {
  const request = ctx.switchToHttp().getRequest() as Request & { user: any };
  return request.user;
});
