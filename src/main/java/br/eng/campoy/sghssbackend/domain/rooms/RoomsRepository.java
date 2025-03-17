package br.eng.campoy.sghssbackend.domain.rooms;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoomsRepository extends JpaRepository<RoomsEntity, Long> {
    List<RoomsEntity> findByServiceUnitId(Long serviceUnitId);
}