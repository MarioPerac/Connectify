import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SlackService {
  private apiUrl = 'http://localhost:8080/api/slack';
  constructor(private http: HttpClient) { }

  public authorize() {
    const url = this.apiUrl + "/authorize";
    window.location.href = url;
  }

  public getToken(code: string){
    const url = this.apiUrl + "/oauth/callback?code=" + code;
    return this.http.get<any>(url);
  }
}
