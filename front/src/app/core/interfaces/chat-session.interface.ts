export interface ChatSession {
  id?: number;
  clientFirstName: string;
  clientLastName: string;
  supportFirstName: string;
  supportLastName: string;
  createdAt?: Date;
  closedAt?: Date;
}
