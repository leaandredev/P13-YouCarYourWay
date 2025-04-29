export interface Message {
  id?: number;
  senderFirstName: string;
  senderLastName: string;
  chatSessionId: number;
  content: string;
  createdAt?: Date;
}
