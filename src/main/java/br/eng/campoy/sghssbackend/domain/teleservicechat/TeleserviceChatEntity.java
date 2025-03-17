package br.eng.campoy.sghssbackend.domain.teleservicechat;

import br.eng.campoy.sghssbackend.domain.teleservice.TeleserviceEntity;
import br.eng.campoy.sghssbackend.domain.teleservicechat.ValueObject.SenderType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TeleserviceChat")
public class TeleserviceChatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teleservice_id", nullable = false)
    private TeleserviceEntity teleservice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SenderType senderType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false)
    private LocalDateTime sentAt;
}