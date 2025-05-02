import { Injectable } from '@angular/core';
import { Client, IMessage } from '@stomp/stompjs';
import { Subject } from 'rxjs';
import SockJS from 'sockjs-client';
import { Message } from '../interfaces/message.interface';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private stompClient: Client | undefined;
  private messageSubject = new Subject<any>();
  private sessionSubject = new Subject<any>();

  connect(sessionId?: number) {
    this.stompClient = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
      reconnectDelay: 5000,
    });

    this.stompClient.onConnect = () => {
      if (sessionId) {
        this.stompClient?.subscribe(
          `/topic/chat/${sessionId}`,
          (msg: IMessage) => {
            console.log('Message reçu côté front', msg.body);
            this.messageSubject.next(JSON.parse(msg.body));
          }
        );
      }

      this.stompClient?.subscribe('/topic/sessions', (msg: IMessage) => {
        console.log('Session reçu côté front', msg.body);
        this.sessionSubject.next(JSON.parse(msg.body));
      });
    };

    this.stompClient.activate();
  }

  sendMessage(sessionId: number, message: Message) {
    console.log('start of WebSocketService.sendMessage');
    console.log(this.stompClient);
    console.log(this.stompClient?.active);
    if (this.stompClient && this.stompClient.active) {
      console.log('websocket send message');

      this.stompClient.publish({
        destination: `/app/message/${sessionId}`,
        body: JSON.stringify(message),
      });
    }
  }

  getMessages() {
    return this.messageSubject.asObservable();
  }

  getSessions() {
    return this.sessionSubject.asObservable();
  }

  disconnect() {
    if (this.stompClient && this.stompClient.active) {
      this.stompClient.deactivate();
    }
  }
}
