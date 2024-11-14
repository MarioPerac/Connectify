import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { User } from '../models/User.model';

@Injectable({
  providedIn: 'root'
})
export class SignUpService {

  private apiUrl = 'https://localhost:8080/api/signup';
  constructor(private http: HttpClient) { }

  public signUp(user: User): Observable<boolean> {
    return this.http.post<boolean>(this.apiUrl, user, { observe: 'response' }).pipe(
      map(response => {
        if (response.status === 200) {
          return true;
        } else {
          return false;
        }
      }));

  }
}
