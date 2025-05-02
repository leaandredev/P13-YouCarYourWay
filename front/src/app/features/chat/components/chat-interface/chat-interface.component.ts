import {
  Component,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  SimpleChanges,
} from '@angular/core';
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
export class ChatInterfaceComponent implements OnInit, OnDestroy, OnChanges {
  @Input() session!: ChatSession;

  messages: Message[] = [];
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
    this.webSocketService.connect(this.session.id);

    this.firstName = this.sessionService.sessionInformation?.firstName;
    this.lastName = this.sessionService.sessionInformation?.lastName;
    this.type = this.sessionService.sessionInformation?.type;

    this.listenToWebSocket();
  }

  private loadMessages() {
    this.chatService
      .getMessages(String(this.session.id))
      .subscribe((messages: Message[]) => {
        console.log(messages);
        this.messages = messages;
      });
  }

  private listenToWebSocket() {
    this.messageSubscription = this.webSocketService
      .getMessages()
      .subscribe((message: any) => {
        this.messages.push(message);
      });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['session'] && changes['session'].currentValue) {
      this.loadMessages();
    }
  }

  sendMessage() {
    this.sendToAPI(this.messageText);
  }

  ngOnDestroy() {
    this.sendToAPI('a quitté la conversation');
    this.messageSubscription.unsubscribe();
    this.webSocketService.disconnect();
    this.sessionService.logOut();
  }

  private sendToAPI(content: string) {
    if (
      this.messageText.trim() !== '' &&
      this.session.id &&
      this.sessionService.sessionInformation
    ) {
      console.log('sendToWebSocket');

      this.webSocketService.sendMessage(this.session.id, {
        senderId: this.sessionService.sessionInformation?.id,
        sessionId: this.session.id,
        senderLastName: this.sessionService.sessionInformation?.lastName,
        senderFirstName: this.sessionService.sessionInformation?.firstName,
        content: content,
      });
    }

    this.messageText = '';
  }
}
