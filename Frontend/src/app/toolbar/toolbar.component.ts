import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Router } from '@angular/router';
import { LoginService } from '../services/login.service';
import { MatIcon, MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-toolbar',
  standalone: true,
  imports: [MatToolbarModule, CommonModule, MatMenuModule,  MatIconModule, MatButtonModule],
  templateUrl: './toolbar.component.html',
  styleUrl: './toolbar.component.css'
})
export class ToolbarComponent {

  constructor(private router: Router, private loginService: LoginService) { }

  onLogoutClick() {
    sessionStorage.clear();
    this.router.navigate(['login']);
  }

  onHomeClick() {
    this.router.navigate(['home']);
  }

  onAccountsClick() {
    this.router.navigate(['accounts']);
  }

  onControlClick() {
    this.router.navigate(['control']);
  }

  onMyAutomationsClick(){
    this.router.navigate(['table']);
  }
}
