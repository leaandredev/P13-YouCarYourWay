package yourcaryourway.com.example.yourcaryourway.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import yourcaryourway.com.example.yourcaryourway.models.ChatMessage;
import yourcaryourway.com.example.yourcaryourway.models.ChatSession;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    public List<ChatMessage> findByChatSession(final ChatSession session);
}
