import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { WebSocketService } from '../core/services/web-socket.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../core/services/auth.service';

@Component({
  selector: 'app-web-socket',
  imports: [CommonModule, FormsModule],
  templateUrl: './web-socket.component.html',
  styleUrl: './web-socket.component.scss',
})
export class WebSocketComponent implements OnInit, OnDestroy {
  messages: any[] = [];
  messageText: string = '';
  username: string = '';
  private messageSubscription!: Subscription;

  constructor(
    private webSocketService: WebSocketService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.username = this.authService.getUsername();

    this.messageSubscription = this.webSocketService
      .getMessages()
      .subscribe((message: any) => {
        console.log(message);

        this.messages.push(message);
      });
  }

  sendMessage() {
    if (this.messageText.trim() !== '') {
      this.webSocketService.sendMessage({
        username: this.username,
        text: this.messageText,
      });
      this.messageText = '';
    }
  }

  ngOnDestroy() {
    this.webSocketService.sendMessage({
      username: this.username,
      text: 'a quitté la conversation',
    });
    this.messageText = '';
    this.messageSubscription.unsubscribe();
    this.webSocketService.closeConnection();
  }
}
