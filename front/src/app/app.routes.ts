import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { WebSocketComponent } from './web-socket/web-socket.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'chat', component: WebSocketComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
];
