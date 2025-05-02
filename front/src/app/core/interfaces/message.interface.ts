export interface Message {
  id?: number;
  senderId: number;
  senderFirstName: string;
  senderLastName: string;
  sessionId: number;
  content: string;
  createdAt?: Date;
}
