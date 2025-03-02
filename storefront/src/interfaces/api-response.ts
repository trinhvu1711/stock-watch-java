/* eslint-disable @typescript-eslint/no-explicit-any */
import { IPaging } from './paging';

//----------------------------------------------------------------------------

export interface IApiResponse<T> {
  data: T;
  paging: IPaging;
  total: number;
  filter: Record<string, any>;
}

export interface IAuthInfoUser {
  userName: string;
}

export interface IStoreFrontAuthResponse {
  isAuthenticated: boolean;
  authenticatedUser: IAuthInfoUser;
}
