package br.eng.campoy.sghssbackend.domain.teleservicechat.dto;

import br.eng.campoy.sghssbackend.domain.teleservicechat.ValueObject.SenderType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TeleserviceChatResponseDto {
    private Long id;
    private String teleserviceInfo;
    private SenderType senderType;
    private String message;
    private LocalDateTime sentAt;
}