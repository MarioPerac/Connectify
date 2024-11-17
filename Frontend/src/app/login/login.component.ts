import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginService } from '../services/login.service';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { User } from '../models/user.model';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ MatCardModule, CommonModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatIconModule, ReactiveFormsModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  public form: FormGroup = new FormGroup({});

  constructor(private formBuilder: FormBuilder, private loginService: LoginService, private snackBar: MatSnackBar, private router: Router) { }


  public ngOnInit(): void {
    this.form = this.formBuilder.group({
      username: [null, Validators.required],
      password: [null, Validators.required]
    })
  }

  public login(form: any) {
    const user = new User(form.value.username, form.value.password);
    this.loginService.login(new User(form.value.username, form.value.password)).subscribe({
      next: (response: any) => {
        sessionStorage.setItem("username", user.username);
        this.router.navigate(['home']);
      },
      error: (error) => {
        if (error.status === 403) {

          this.snackBar.open("Incorrect credentials", undefined, {
            duration: 2000
          });
        }
        else {

          console.error('Error during login:', error);
          this.snackBar.open("An error occurred. Please try again.", undefined, {
            duration: 2000
          });
        }
      }
    });
  }


  hide = true;

  clickEvent(event: MouseEvent) {
    this.hide = !this.hide;
    event.stopPropagation();
  }

  signup() {
    this.router.navigate(["/signup"]);
  }
}
