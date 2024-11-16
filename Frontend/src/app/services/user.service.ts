import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://localhost:8080/api/users';
  constructor(private http: HttpClient) { }

  public getAvailableAutomations(username: String) : Observable<string[]> {
    const url = this.apiUrl + "/" + username  +"/available-automations";
    return this.http.get<string[]>(url);

  }
}

