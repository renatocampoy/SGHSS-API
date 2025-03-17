package br.eng.campoy.sghssbackend.domain.teleservicechat.dto;

import br.eng.campoy.sghssbackend.domain.teleservicechat.ValueObject.SenderType;
import lombok.Data;

@Data
public class TeleserviceChatDto {
    private Long teleserviceId;
    private SenderType senderType;
    private String message;
}