import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SignUpService } from '../services/sign-up.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatCardModule } from '@angular/material/card';
import { MatFormField, MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { User } from '../models/User.model';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [ MatCardModule, MatFormFieldModule,CommonModule, MatInputModule, MatButtonModule, MatIconModule, ReactiveFormsModule, HttpClientModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent  implements OnInit {

  public form: FormGroup = new FormGroup({});
  constructor(private fromBuilder: FormBuilder, private signUpService: SignUpService, private router: Router, private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.form = this.fromBuilder.group({
      username: [null, Validators.required],
      password: [null, Validators.required]
    })
  }

  public signup(form: any) {
    const user = new User(
      this.form.value.username,
      this.form.value.password
    );

    this.signUpService.signUp(user).subscribe({
      next: (success: boolean) => {
        if (success) {
          this.router.navigate(['login']);
          this.snackBar.open('Successful Sign In', undefined, {
            duration: 2000
          });
        } else {
          this.snackBar.open('Unsuccessful Sign In', undefined, {
            duration: 2000
          });
        }
      },
      error: (err) => {
        console.error('Sign up error', err);
        this.snackBar.open('Error during sign up', undefined, {
          duration: 2000
        });
      }
    });

  }

  hide = true;

  clickEvent(event: MouseEvent) {
    this.hide = !this.hide;
    event.stopPropagation();
  }

}