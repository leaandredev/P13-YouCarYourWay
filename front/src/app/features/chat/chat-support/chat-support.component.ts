import { Component, OnDestroy, OnInit } from '@angular/core';
import { ChatInterfaceComponent } from '../components/chat-interface/chat-interface.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChatService } from '../../../core/services/chat.service';
import { ChatSession } from '../../../core/interfaces/chat-session.interface';
import { SessionService } from '../../../core/services/session.service';

@Component({
  selector: 'app-chat-support',
  imports: [ChatInterfaceComponent, CommonModule, FormsModule],
  templateUrl: './chat-support.component.html',
  styleUrl: './chat-support.component.scss',
})
export class ChatSupportComponent implements OnInit, OnDestroy {
  chatSessions: ChatSession[] = [];
  selectedSession?: ChatSession;

  constructor(
    private chatService: ChatService,
    private sessionService: SessionService
  ) {}

  ngOnInit() {
    this.chatService.getOpenSessions().subscribe((sessions) => {
      this.chatSessions = sessions;
    });
  }

  selectSession(session: ChatSession) {
    this.selectedSession = session;
  }

  ngOnDestroy() {
    this.sessionService.logOut();
  }
}
