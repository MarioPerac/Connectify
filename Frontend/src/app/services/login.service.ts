import { Injectable } from '@angular/core';
import { User } from '../models/User.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private apiUrl = 'https://localhost:8080/api/login';

  constructor(private http: HttpClient) { }

  public login(user: User) {
    return this.http.post<any>(this.apiUrl, user);
  }
}
