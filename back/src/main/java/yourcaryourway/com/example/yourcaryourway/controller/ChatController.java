package yourcaryourway.com.example.yourcaryourway.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import yourcaryourway.com.example.yourcaryourway.dto.SendMessageRequest;
import yourcaryourway.com.example.yourcaryourway.dto.CreateSessionRequest;
import yourcaryourway.com.example.yourcaryourway.models.ChatMessage;
import yourcaryourway.com.example.yourcaryourway.models.ChatSession;
import yourcaryourway.com.example.yourcaryourway.models.Client;
import yourcaryourway.com.example.yourcaryourway.models.Support;
import yourcaryourway.com.example.yourcaryourway.models.User;
import yourcaryourway.com.example.yourcaryourway.services.ChatService;
import yourcaryourway.com.example.yourcaryourway.services.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;

    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @PostMapping("/session")
    public ResponseEntity<ChatSession> createSession(@RequestBody CreateSessionRequest request) {
        Client client = this.userService.findClientById(request.getClientId());
        ChatSession session = this.chatService.createSession(client);
        return ResponseEntity.ok(session);
    }

    @PostMapping("/message")
    public ResponseEntity<Void> sendMessage(@RequestBody SendMessageRequest request) {
        ChatSession session = this.chatService.getSessionById(request.getSessionId());
        User sender = this.userService.findUserById(request.getSenderId());
        ChatMessage message = ChatMessage.builder()
                .sender(sender)
                .chatSession(session)
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build();
        if (session.getSupport() == null && sender instanceof Support) {
            session.setSupport((Support) sender);
            this.chatService.saveSession(session);
        }
        this.chatService.saveMessage(message);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sessions/open")
    public ResponseEntity<List<ChatSession>> getOpenSessions() {
        List<ChatSession> openSessions = this.chatService.getOpenSessions();
        return ResponseEntity.ok(openSessions);
    }

    @GetMapping("/messages/{sessionId}")
    public ResponseEntity<List<ChatMessage>> getMessages(@PathVariable Long sessionId) {
        ChatSession session = this.chatService.getSessionById(sessionId);
        List<ChatMessage> messages = this.chatService.getMessagesForSession(session);
        return ResponseEntity.ok(messages);
    }

    @PatchMapping("/session/{sessionId}/close")
    public ResponseEntity<Void> closeSession(@PathVariable Long sessionId) {
        ChatSession session = this.chatService.getSessionById(sessionId);
        this.chatService.closeSession(session);
        return ResponseEntity.ok().build();
    }

}
