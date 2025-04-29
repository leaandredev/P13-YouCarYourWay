import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { WebSocketService } from '../../../../core/services/web-socket.service';
import { SessionService } from '../../../../core/services/session.service';
import { ChatSession } from '../../../../core/interfaces/chat-session.interface';
import { ChatService } from '../../../../core/services/chat.service';
import { Message } from '../../../../core/interfaces/message.interface';

@Component({
  selector: 'app-chat-interface',
  imports: [CommonModule, FormsModule],
  templateUrl: './chat-interface.component.html',
  styleUrl: './chat-interface.component.scss',
})
export class ChatInterfaceComponent implements OnInit, OnDestroy {
  @Input() session!: ChatSession;

  messages: any[] = [];
  messageText: string = '';
  firstName?: string = '';
  lastName?: string = '';
  type?: string = 'UNKNOWN';
  private messageSubscription!: Subscription;

  constructor(
    private webSocketService: WebSocketService,
    private sessionService: SessionService,
    private chatService: ChatService
  ) {}

  ngOnInit() {
    this.firstName = this.sessionService.sessionInformation?.firstName;
    this.lastName = this.sessionService.sessionInformation?.lastName;
    this.type = this.sessionService.sessionInformation?.type;

    this.chatService
      .getMessages(String(this.session.id))
      .subscribe((messages: Message[]) => {
        this.messages = messages;
      });

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
      this.chatService.sendMessage({
        sessionId: this.session.id,
        senderId: this.sessionService.sessionInformation?.id,
        content: this.messageText,
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
