import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { UserService } from '../services/user.service';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-automation',
  standalone: true,
  imports: [ MatFormFieldModule, MatSelectModule, MatCardModule, MatInputModule, CommonModule, MatInputModule, MatButtonModule, MatIconModule, ReactiveFormsModule],
  templateUrl: './automation.component.html',
  styleUrl: './automation.component.css'
})
export class AutomationComponent implements OnInit {
  types: string[] = ["create issue - send message"]; 
  selectedType: string = ''; 
  constructor(private userService: UserService, private loginService: LoginService, private router: Router) {}

  ngOnInit(): void {
    this.userService.getAvailableAutomations(this.loginService.activeUser!.username).subscribe({
      next: (data: string[]) => {
        this.types = data; 
      },
      error: (err) => {
        console.error('Failed to load automation types:', err);
      }
    });;
  }

  onAddClick(): void {
    this.router.navigate(["/jira-auth"]);
  }
}
