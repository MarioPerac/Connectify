import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { AutomationComponent } from './automation/automation.component';
import { JiraAuthCardComponent } from './jira-auth-card/jira-auth-card.component';
import { JiraProjectsComponent } from './jira-projects/jira-projects.component';
import { SlackAuthComponent } from './slack-auth/slack-auth.component';

export const routes: Routes = [
    {
        path: '', redirectTo: '/login', pathMatch: 'full'
      },
      {
        path: 'login',
        component: LoginComponent
      },
      {
        path: 'signup',
        component: SignupComponent
      },
      {
        path: 'home',
        component: AutomationComponent
      },
      {
        path: 'jira-auth',
        component: JiraAuthCardComponent
      },
      {
        path: 'api/jira/oauth/callback',
        component: JiraProjectsComponent
      },
      {
        path: 'slack-auth',
        component: SlackAuthComponent
      }
];
