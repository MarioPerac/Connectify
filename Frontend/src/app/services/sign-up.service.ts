import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class SignUpService {

  private apiUrl = 'http://localhost:8080/api/signup';
  constructor(private http: HttpClient) { }

  public signUp(user: User): Observable<boolean> {
    return this.http.post<boolean>(this.apiUrl, user, { observe: 'response' }).pipe(
      map(response => {
        if (response.status === 201) {
          return true;
        } else {
          return false;
        }
      }));

  }
}
