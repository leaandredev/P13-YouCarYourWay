import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ChatSession } from '../interfaces/chat-session.interface';
import { Message } from '../interfaces/message.interface';

@Injectable({
  providedIn: 'root',
})
export class ChatService {
  private pathService = 'http://' + environment.baseUrl + '/chat';

  constructor(private httpClient: HttpClient) {}

  public createSession(clientId: string): Observable<ChatSession> {
    return this.httpClient.post<ChatSession>(
      `${this.pathService}/session/${clientId}`,
      null
    );
  }

  public sendMessage(message: any): Observable<Message> {
    console.log('send message : ', message);

    return this.httpClient.post<Message>(
      `${this.pathService}/message`,
      message
    );
  }

  public getOpenSessions(): Observable<ChatSession[]> {
    return this.httpClient.get<ChatSession[]>(
      `${this.pathService}/sessions/open`
    );
  }

  public getMessages(sessionId: string): Observable<Message[]> {
    return this.httpClient.get<Message[]>(
      `${this.pathService}/messages/${sessionId}`
    );
  }

  public closeSession(sessionId: string): Observable<void> {
    return this.httpClient.patch<void>(
      `${this.pathService}/session/${sessionId}/close`,
      null
    );
  }
}
