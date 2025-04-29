package yourcaryourway.com.example.yourcaryourway.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
import yourcaryourway.com.example.yourcaryourway.dto.SessionResponse;
import yourcaryourway.com.example.yourcaryourway.dto.CreateSessionRequest;
import yourcaryourway.com.example.yourcaryourway.dto.MessageResponse;
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

    @PostMapping("/session/{clientId}")
    public ResponseEntity<SessionResponse> createSession(@PathVariable Long clientId) {
        Client client = this.userService.findClientById(clientId);
        ChatSession session = this.chatService.createSession(client);

        SessionResponse response = new SessionResponse();
        response.setId(session.getId());
        response.setClientFirstName(client.getFirstName());
        response.setClientLastName(client.getLastName());
        response.setCreatedAt(session.getCreatedAt());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/message")
    public ResponseEntity<Void> sendMessage(@RequestBody SendMessageRequest request) {
        System.out.println("Message reçu API : " + request.getContent());
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
    public ResponseEntity<List<SessionResponse>> getOpenSessions() {
        List<ChatSession> openSessions = this.chatService.getOpenSessions();
        List<SessionResponse> sessionDTOs = openSessions.stream()
                .map(session -> {
                    SessionResponse dto = new SessionResponse();
                    dto.setId(session.getId());
                    dto.setClientFirstName(session.getClient().getFirstName());
                    dto.setClientLastName(session.getClient().getLastName());
                    dto.setCreatedAt(session.getCreatedAt());
                    dto.setClosedAt(session.getClosedAt());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(sessionDTOs);
    }

    @GetMapping("/messages/{sessionId}")
    public ResponseEntity<List<MessageResponse>> getMessages(@PathVariable Long sessionId) {
        ChatSession session = this.chatService.getSessionById(sessionId);
        List<ChatMessage> messages = this.chatService.getMessagesForSession(session);
        List<MessageResponse> messageDTOs = messages.stream()
                .map(message -> {
                    MessageResponse dto = new MessageResponse();
                    dto.setId(message.getId());
                    dto.setContent(message.getContent());
                    dto.setSenderFirstName(message.getSender().getFirstName());
                    dto.setSenderLastName(message.getSender().getLastName());
                    dto.setCreatedAt(message.getCreatedAt());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(messageDTOs);
    }

    @PatchMapping("/session/{sessionId}/close")
    public ResponseEntity<Void> closeSession(@PathVariable Long sessionId) {
        ChatSession session = this.chatService.getSessionById(sessionId);
        this.chatService.closeSession(session);
        return ResponseEntity.ok().build();
    }

}
