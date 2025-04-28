import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { WebSocketService } from '../core/services/web-socket.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SessionService } from '../core/services/session.service';

@Component({
  selector: 'app-web-socket',
  imports: [CommonModule, FormsModule],
  templateUrl: './web-socket.component.html',
  styleUrl: './web-socket.component.scss',
})
export class WebSocketComponent implements OnInit, OnDestroy {
  messages: any[] = [];
  messageText: string = '';
  firstName?: string = '';
  lastName?: string = '';
  private messageSubscription!: Subscription;

  constructor(
    private webSocketService: WebSocketService,
    private sessionService: SessionService
  ) {}

  ngOnInit() {
    this.firstName = this.sessionService.sessionInformation?.firstName;
    this.lastName = this.sessionService.sessionInformation?.lastName;

    this.messageSubscription = this.webSocketService
      .getMessages()
      .subscribe((message: any) => {
        this.messages.push(message);
      });
  }

  sendMessage() {
    if (this.messageText.trim() !== '') {
      this.webSocketService.sendMessage({
        id: this.sessionService.sessionInformation?.id,
        text: this.messageText,
      });
      this.messageText = '';
    }
  }

  ngOnDestroy() {
    this.webSocketService.sendMessage({
      id: this.sessionService.sessionInformation?.id,
      text: 'a quitté la conversation',
    });
    this.messageText = '';
    this.messageSubscription.unsubscribe();
    this.webSocketService.closeConnection();
  }
}
