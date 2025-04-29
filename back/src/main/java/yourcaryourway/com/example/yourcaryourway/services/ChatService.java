package yourcaryourway.com.example.yourcaryourway.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yourcaryourway.com.example.yourcaryourway.exception.NoEntryFoundException;
import yourcaryourway.com.example.yourcaryourway.models.ChatMessage;
import yourcaryourway.com.example.yourcaryourway.models.ChatSession;
import yourcaryourway.com.example.yourcaryourway.models.Client;
import yourcaryourway.com.example.yourcaryourway.models.User;
import yourcaryourway.com.example.yourcaryourway.repository.ChatMessageRepository;
import yourcaryourway.com.example.yourcaryourway.repository.ChatSessionRepository;
import yourcaryourway.com.example.yourcaryourway.repository.UserRepository;

@Service
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatSessionRepository chatSessionRepository;

    public ChatService(ChatMessageRepository chatMessageRepository, ChatSessionRepository chatSessionRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatSessionRepository = chatSessionRepository;
    }

    public ChatSession getSessionById(long sessionId) {
        return this.chatSessionRepository.findById(sessionId)
                .orElseThrow(() -> new NoEntryFoundException("The session does not exist"));
    }

    public ChatSession createSession(final Client client) {
        ChatSession chatSession = ChatSession.builder()
                .client(client)
                .createdAt(LocalDateTime.now())
                .build();
        return this.chatSessionRepository.save(chatSession);
    }

    public void saveSession(final ChatSession session) {
        this.chatSessionRepository.save(session);
    }

    public void saveMessage(final ChatMessage chatMessage) {
        this.chatMessageRepository.save(chatMessage);
    }

    public List<ChatSession> getOpenSessions() {
        return this.chatSessionRepository.findByClosedAtIsNull();
    }

    public List<ChatMessage> getMessagesForSession(ChatSession session) {
        return this.chatMessageRepository.findByChatSession(session);
    }

    public void closeSession(ChatSession session) {
        session.setClosedAt(LocalDateTime.now());
        this.chatSessionRepository.save(session);
    }

}
