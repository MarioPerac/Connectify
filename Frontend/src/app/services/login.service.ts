import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  public activeUser: User | null = null;
  private apiUrl = 'http://localhost:8080/api/login';

  constructor(private http: HttpClient) { }

  public login(user: User) {
    return this.http.post<any>(this.apiUrl, user);
  }
}
