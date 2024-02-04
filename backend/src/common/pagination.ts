import { ExecutionContext, createParamDecorator } from '@nestjs/common';

export interface Pageable {
  page: number;
  pageSize: number;
}

export const offsetOf = (pageable: Pageable) => {
  return (pageable.page - 1) * pageable.pageSize;
};

export const PageableDefault = createParamDecorator((data: unknown, ctx: ExecutionContext) => {
  const request = ctx.switchToHttp().getRequest();

  const { pageSize, page } = request.query;

  const result = {
    pageSize: Number(pageSize ?? 10),
    page: Number(page ?? 1),
  } as Pageable;

  return result;
});

export class Pagination<T> implements Pageable {
  content: T[];
  total: number;
  pageSize: number;
  page: number;

  static of<T>(content: T[], total: number, pageable: Pageable) {
    const result = new Pagination<T>();

    result.content = content;
    result.page = pageable.page;
    result.pageSize = pageable.pageSize;
    result.total = total;

    return result;
  }
}
