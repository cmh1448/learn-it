export interface Pageable {
  page?: number;
  pageSize?: number;
}

export class Pagination<T> implements Pageable {
  content: T[];
  total: number;
  pageSize: number;
  page: number;
}
