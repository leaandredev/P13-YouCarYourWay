import { Routes } from '@angular/router';
import { authRoutes } from './features/auth/auth.routes';
import { chatRoutes } from './features/chat/chat.routes';
import { UnauthGuard } from './core/guards/unauth.guard';
import { AuthGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: '',
    canActivate: [UnauthGuard],
    children: authRoutes,
  },
  {
    path: 'chat',
    canActivate: [AuthGuard],
    children: chatRoutes,
  },
];
