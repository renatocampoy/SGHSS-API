package br.eng.campoy.sghssbackend.domain.teleservicechat;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TeleserviceChatRepository extends JpaRepository<TeleserviceChatEntity, Long> {
    List<TeleserviceChatEntity> findByTeleserviceId(Long teleserviceId);
}