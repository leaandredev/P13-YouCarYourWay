package yourcaryourway.com.example.yourcaryourway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import yourcaryourway.com.example.yourcaryourway.models.ChatSession;
import java.util.List;

@Repository
public interface ChatSessionRepository extends JpaRepository<ChatSession, Long> {

    List<ChatSession> findByClosedAtIsNotNull();

}
