export interface Message {
  id?: number;
  senderFirstName: string;
  senderLastName: string;
  sessionId: number;
  content: string;
  createdAt?: Date;
}
