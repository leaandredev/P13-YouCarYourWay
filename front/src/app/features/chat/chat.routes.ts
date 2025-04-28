import { Routes } from '@angular/router';
import { WebSocketComponent } from './web-socket/web-socket.component';

export const chatRoutes: Routes = [
  {
    path: '',
    component: WebSocketComponent,
  },
];
