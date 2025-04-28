import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { WebSocketService } from '../../../core/services/web-socket.service';
import { SessionService } from '../../../core/services/session.service';

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
  type?: string = 'UNKNOWN';
  private messageSubscription!: Subscription;

  constructor(
    private webSocketService: WebSocketService,
    private sessionService: SessionService
  ) {}

  ngOnInit() {
    this.firstName = this.sessionService.sessionInformation?.firstName;
    this.lastName = this.sessionService.sessionInformation?.lastName;
    this.type = this.sessionService.sessionInformation?.type;

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
        firstName: this.sessionService.sessionInformation?.firstName,
        lastName: this.sessionService.sessionInformation?.lastName,
        text: this.messageText,
      });
      this.messageText = '';
    }
  }

  ngOnDestroy() {
    this.webSocketService.sendMessage({
      id: this.sessionService.sessionInformation?.id,
      firstName: this.sessionService.sessionInformation?.firstName,
      lastName: this.sessionService.sessionInformation?.lastName,
      text: 'a quitté la conversation',
    });
    this.messageText = '';
    this.messageSubscription.unsubscribe();
    this.webSocketService.closeConnection();
    this.sessionService.logOut();
  }
}
