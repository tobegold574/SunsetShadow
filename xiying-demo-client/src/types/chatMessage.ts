export interface ChatMessage {
    id?: string;
    content: string;
    sender: string;
    type: string;
    recipient?: string;
  }