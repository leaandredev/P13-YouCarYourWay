import { Routes } from '@angular/router';
import { ChatClientComponent } from './chat-client/chat-client.component';
import { ChatSupportComponent } from './chat-support/chat-support.component';

export const chatRoutes: Routes = [
  {
    path: 'chat-client',
    component: ChatClientComponent,
  },
  {
    path: 'chat-support',
    component: ChatSupportComponent,
  },
];
