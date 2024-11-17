import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { SlackService } from '../services/slack.service';
import { ToolbarComponent } from '../toolbar/toolbar.component';

@Component({
  selector: 'app-slack-auth',
  standalone: true,
  imports: [MatButtonModule, MatCardModule, ToolbarComponent],
  templateUrl: './slack-auth.component.html',
  styleUrl: './slack-auth.component.css'
})
export class SlackAuthComponent {
  constructor(private slackService: SlackService ){

  }
  onAuthorizeClick(): void {
    this.slackService.authorize();
  }
}
