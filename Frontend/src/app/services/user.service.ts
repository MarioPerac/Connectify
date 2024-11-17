import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';
import { AutomationRequest } from '../models/automation_request.model';
import { Automation } from '../models/automation.model';

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

  public addAutomation(automation: AutomationRequest){
    const url = this.apiUrl + "/add-automation";
    return this.http.post(url, automation);
  }

  public getAutomations(): Observable<Automation[]> {
    const url = this.apiUrl + "/" + sessionStorage.getItem("username") + "/automations";
    return this.http.get<Automation[]>(url);
  }
}

