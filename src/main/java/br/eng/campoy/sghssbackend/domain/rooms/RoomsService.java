package br.eng.campoy.sghssbackend.domain.rooms;

import br.eng.campoy.sghssbackend.domain.rooms.dto.RoomsDto;
import br.eng.campoy.sghssbackend.domain.rooms.dto.RoomsResponseDto;
import br.eng.campoy.sghssbackend.domain.rooms.ValueObject.RoomStatus;
import br.eng.campoy.sghssbackend.domain.rooms.exception.RoomsException;
import br.eng.campoy.sghssbackend.domain.serviceunit.ServiceUnitEntity;
import br.eng.campoy.sghssbackend.domain.serviceunit.ServiceUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomsService {

    private final RoomsRepository roomsRepository;
    private final ServiceUnitRepository serviceUnitRepository;

    @Autowired
    public RoomsService(RoomsRepository roomsRepository, ServiceUnitRepository serviceUnitRepository) {
        this.roomsRepository = roomsRepository;
        this.serviceUnitRepository = serviceUnitRepository;
    }

    public RoomsResponseDto createRoom(RoomsDto dto) {
        ServiceUnitEntity serviceUnit = serviceUnitRepository.findById(dto.getServiceUnitId())
                .orElseThrow(() -> new RoomsException("Unidade de saúde não encontrada."));

        RoomsEntity room = new RoomsEntity();
        room.setServiceUnit(serviceUnit);
        room.setRoomNumber(dto.getRoomNumber());
        room.setCapacity(dto.getCapacity());
        room.setStatus(RoomStatus.LIVRE);

        return convertToDto(roomsRepository.save(room));
    }

    public RoomsResponseDto updateRoom(Long id, RoomsDto dto) {
        RoomsEntity room = roomsRepository.findById(id)
                .orElseThrow(() -> new RoomsException("Quarto não encontrado com ID: " + id));

        room.setRoomNumber(dto.getRoomNumber());
        room.setCapacity(dto.getCapacity());
        room.setStatus(dto.getStatus());

        return convertToDto(roomsRepository.save(room));
    }

    public void deleteRoom(Long id) {
        RoomsEntity room = roomsRepository.findById(id)
                .orElseThrow(() -> new RoomsException("Quarto não encontrado com ID: " + id));

        roomsRepository.delete(room);
    }

    public Optional<RoomsResponseDto> getRoomById(Long id) {
        return roomsRepository.findById(id).map(this::convertToDto);
    }

    public List<RoomsResponseDto> listRooms() {
        return roomsRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<RoomsResponseDto> listByServiceUnit(Long serviceUnitId) {
        return roomsRepository.findByServiceUnitId(serviceUnitId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private RoomsResponseDto convertToDto(RoomsEntity entity) {
        return new RoomsResponseDto(
                entity.getId(),
                entity.getServiceUnit().getName(),
                entity.getRoomNumber(),
                entity.getCapacity(),
                entity.getStatus()
        );
    }
}