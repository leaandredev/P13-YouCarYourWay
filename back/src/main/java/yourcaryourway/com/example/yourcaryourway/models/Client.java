package yourcaryourway.com.example.yourcaryourway.models;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;

@Entity
@Table(name = "client")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@ToString
public class Client extends User {
}
