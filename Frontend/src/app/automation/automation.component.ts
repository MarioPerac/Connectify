import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { LoginService } from '../services/login.service';
import { ToolbarComponent } from '../toolbar/toolbar.component';
import { MatCheckboxModule } from '@angular/material/checkbox';

@Component({
  selector: 'app-automation',
  standalone: true,
  imports: [
    ToolbarComponent, 
    MatFormFieldModule, 
    MatSelectModule, 
    MatCardModule, 
    MatInputModule, 
    CommonModule, 
    MatButtonModule, 
    MatIconModule, 
    ReactiveFormsModule,
    MatCheckboxModule
  ],
  templateUrl: './automation.component.html',
  styleUrls: ['./automation.component.css']
})
export class AutomationComponent implements OnInit {
  types: string[] = [];  // Define types as an empty array initially
  form: FormGroup;
  isButtonDisabled: boolean = true;

  constructor(private fb: FormBuilder, 
              private userService: UserService, 
              private loginService: LoginService, 
              private router: Router) {
    // Initialize form with an empty FormArray
    this.form = this.fb.group({
      automations: this.fb.array([], [Validators.required])
    });
  }

  ngOnInit(): void {
    const username = sessionStorage.getItem("username");
    if (username) {
      this.userService.getAvailableAutomations(username).subscribe({
        next: (data: string[]) => {
          this.types = data && data.length ? data : [];
          this.setCheckboxes();  // Set the checkboxes once data is loaded
        },
        error: (err) => {
          console.error('Failed to load automation types:', err);
        }
      });
    }
  }

  // Dynamically create checkboxes based on types
  setCheckboxes() {
    const control = <FormArray>this.form.controls['automations'];

    // Clear any existing controls before adding new ones
    control.clear();

    // Add a FormControl for each type in the `types` array
    this.types.forEach((type, index) => {
      control.push(this.fb.control(false)); // Push a new checkbox control
    });

    this.form.get('automations')?.valueChanges.subscribe(value => {
      this.isButtonDisabled = !value.includes(true);  // Check if any checkbox is selected
    });
  }

  // Get selected types (where the checkbox is checked)
  getSelectedTypes(): string[] {
    const selectedTypes = this.form.value.automations
      .map((checked: boolean, index: number) => checked ? this.types[index] : null)
      .filter((value: string | null) => value !== null);
    return selectedTypes;
  }

  // When the 'Add' button is clicked
  onAddClick() {
    const selectedTypes = this.getSelectedTypes();
    console.log('Selected Types:', selectedTypes);
    this.router.navigate(["/jira-auth"]);
    sessionStorage.setItem("types", JSON.stringify(selectedTypes)); // Store selected types in session
  }

}
