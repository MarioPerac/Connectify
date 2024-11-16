import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { SlackService } from '../services/slack.service';

@Component({
  selector: 'app-slack-auth',
  standalone: true,
  imports: [MatButtonModule, MatCardModule],
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
