package br.eng.campoy.sghssbackend.domain.roomsoccupancy;

import br.eng.campoy.sghssbackend.domain.roomsoccupancy.ValueObject.RoomsOccupancyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoomsOccupancyRepository extends JpaRepository<RoomsOccupancyEntity, Long> {
    List<RoomsOccupancyEntity> findByPatientId(Long patientId);
    List<RoomsOccupancyEntity> findByRoomId(Long roomId);
    boolean existsByRoomIdAndStatus(Long roomId, RoomsOccupancyStatus status);
}