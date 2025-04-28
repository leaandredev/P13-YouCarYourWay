import { RouterModule, Routes } from '@angular/router';
import { WebSocketComponent } from './web-socket/web-socket.component';
import { NgModule } from '@angular/core';

const routes: Routes = [{ path: 'chat', component: WebSocketComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ChatRoutingModule {}
