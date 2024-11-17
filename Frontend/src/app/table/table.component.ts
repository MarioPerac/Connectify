import { Component } from '@angular/core';
import { ToolbarComponent } from '../toolbar/toolbar.component';
import { MatTableModule } from '@angular/material/table';
import { CommonModule } from '@angular/common';
import { UserService } from '../services/user.service';
import { Automation } from '../models/automation.model';

@Component({
  selector: 'app-table',
  standalone: true,
  imports: [ToolbarComponent, MatTableModule, CommonModule],
  templateUrl: './table.component.html',
  styleUrl: './table.component.css'
})
export class TableComponent {
  automations: Automation[] = [];
  displayedColumns: string[] = ['id', 'jiraProject', 'typesName', 'createdAt', 'actions'];

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadAutomations();
  }

  loadAutomations(): void {
    this.userService.getAutomations().subscribe((data: Automation[]) => {
      this.automations = data;
    });
  }

  deleteAutomation(id: string): void {
    this.userService.deleteAutomation(id).subscribe(() => {
      this.automations = this.automations.filter((automation) => automation.id !== id);
    });
  }
}
