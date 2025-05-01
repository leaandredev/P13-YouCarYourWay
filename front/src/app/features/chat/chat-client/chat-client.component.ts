import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SessionService } from '../../../core/services/session.service';
import { ChatInterfaceComponent } from '../components/chat-interface/chat-interface.component';
import { ChatService } from '../../../core/services/chat.service';
import { ChatSession } from '../../../core/interfaces/chat-session.interface';
import { Router } from '@angular/router';

@Component({
  selector: 'app-chat-client',
  imports: [ChatInterfaceComponent, CommonModule],
  templateUrl: './chat-client.component.html',
  styleUrl: './chat-client.component.scss',
})
export class ChatClientComponent implements OnInit, OnDestroy {
  session!: ChatSession;

  constructor(
    private chatService: ChatService,
    private sessionService: SessionService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.chatService
      .createSession(String(this.sessionService.sessionInformation?.id))
      .subscribe({
        next: (session) => {
          this.session = session;
          console.log('Session créée :', session);
        },
        error: (err) => {
          console.error('Erreur lors de la création de la session :', err);
        },
      });
  }

  public logout(): void {
    this.ngOnDestroy();
    this.router.navigate(['/login']);
  }

  ngOnDestroy() {
    this.chatService.closeSession(String(this.session.id)).subscribe({
      next: () => {
        console.log('Session fermée avec succès.');
      },
      error: (err) => {
        console.error('Erreur lors de la fermeture de la session :', err);
      },
    });
    this.sessionService.logOut();
  }
}
