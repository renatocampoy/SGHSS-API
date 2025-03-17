package br.eng.campoy.sghssbackend.domain.teleservicechat;

import br.eng.campoy.sghssbackend.domain.teleservice.TeleserviceEntity;
import br.eng.campoy.sghssbackend.domain.teleservice.TeleserviceRepository;
import br.eng.campoy.sghssbackend.domain.teleservicechat.dto.TeleserviceChatDto;
import br.eng.campoy.sghssbackend.domain.teleservicechat.dto.TeleserviceChatResponseDto;
import br.eng.campoy.sghssbackend.domain.teleservicechat.exception.TeleserviceChatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeleserviceChatService {

    private final TeleserviceChatRepository teleserviceChatRepository;
    private final TeleserviceRepository teleserviceRepository;

    @Autowired
    public TeleserviceChatService(TeleserviceChatRepository teleserviceChatRepository,
                                  TeleserviceRepository teleserviceRepository) {
        this.teleserviceChatRepository = teleserviceChatRepository;
        this.teleserviceRepository = teleserviceRepository;
    }

    public TeleserviceChatResponseDto sendMessage(TeleserviceChatDto dto) {
        TeleserviceEntity teleservice = teleserviceRepository.findById(dto.getTeleserviceId())
                .orElseThrow(() -> new TeleserviceChatException("Teleatendimento não encontrado."));

        TeleserviceChatEntity message = new TeleserviceChatEntity();
        message.setTeleservice(teleservice);
        message.setSenderType(dto.getSenderType());
        message.setMessage(dto.getMessage());
        message.setSentAt(LocalDateTime.now());

        return convertToDto(teleserviceChatRepository.save(message));
    }

    public List<TeleserviceChatResponseDto> listMessages(Long teleserviceId) {
        return teleserviceChatRepository.findByTeleserviceId(teleserviceId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private TeleserviceChatResponseDto convertToDto(TeleserviceChatEntity entity) {
        return new TeleserviceChatResponseDto(
                entity.getId(),
                "Teleatendimento ID: " + entity.getTeleservice().getId(),
                entity.getSenderType(),
                entity.getMessage(),
                entity.getSentAt()
        );
    }
}