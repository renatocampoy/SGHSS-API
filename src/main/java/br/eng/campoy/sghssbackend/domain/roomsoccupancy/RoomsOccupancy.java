package br.eng.campoy.sghssbackend.domain.roomsoccupancy;

import br.eng.campoy.sghssbackend.domain.roomsoccupancy.dto.RoomsOccupancyDto;
import br.eng.campoy.sghssbackend.domain.roomsoccupancy.dto.RoomsOccupancyResponseDto;
import br.eng.campoy.sghssbackend.domain.roomsoccupancy.ValueObject.RoomsOccupancyStatus;
import br.eng.campoy.sghssbackend.domain.patients.PatientsEntity;
import br.eng.campoy.sghssbackend.domain.patients.PatientsRepository;
import br.eng.campoy.sghssbackend.domain.rooms.RoomsEntity;
import br.eng.campoy.sghssbackend.domain.rooms.RoomsRepository;
import br.eng.campoy.sghssbackend.domain.rooms.ValueObject.RoomStatus;
import br.eng.campoy.sghssbackend.domain.roomsoccupancy.exception.RoomsOccupancyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomsOccupancy {

    private final RoomsOccupancyRepository roomsOccupancyRepository;
    private final PatientsRepository patientsRepository;
    private final RoomsRepository roomsRepository;

    @Autowired
    public RoomsOccupancy(RoomsOccupancyRepository roomsOccupancyRepository,
                          PatientsRepository patientsRepository,
                          RoomsRepository roomsRepository) {
        this.roomsOccupancyRepository = roomsOccupancyRepository;
        this.patientsRepository = patientsRepository;
        this.roomsRepository = roomsRepository;
    }

    public RoomsOccupancyResponseDto registerOccupancy(RoomsOccupancyDto dto) {
        if (roomsOccupancyRepository.existsByRoomIdAndStatus(dto.getRoomId(), RoomsOccupancyStatus.ATIVA)) {
            throw new RoomsOccupancyException("O quarto já está ocupado.");
        }

        PatientsEntity patient = patientsRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RoomsOccupancyException("Paciente não encontrado."));

        RoomsEntity room = roomsRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RoomsOccupancyException("Quarto não encontrado."));

        room.setStatus(RoomStatus.OCUPADO);
        roomsRepository.save(room);

        RoomsOccupancyEntity occupancy = new RoomsOccupancyEntity();
        occupancy.setPatient(patient);
        occupancy.setRoom(room);
        occupancy.setStatus(RoomsOccupancyStatus.ATIVA);
        occupancy.setCheckInDate(dto.getCheckInDate());
        occupancy.setCreatedAt(LocalDateTime.now());
        occupancy.setUpdatedAt(LocalDateTime.now());

        return convertToDto(roomsOccupancyRepository.save(occupancy));
    }

    public void releaseRoom(Long id) {
        RoomsOccupancyEntity occupancy = roomsOccupancyRepository.findById(id)
                .orElseThrow(() -> new RoomsOccupancyException("Ocupação não encontrada."));

        occupancy.setStatus(RoomsOccupancyStatus.FINALIZADA);
        occupancy.setCheckOutDate(LocalDateTime.now());
        occupancy.setUpdatedAt(LocalDateTime.now());

        RoomsEntity room = occupancy.getRoom();
        room.setStatus(RoomStatus.LIVRE);
        roomsRepository.save(room);

        roomsOccupancyRepository.save(occupancy);
    }

    public Optional<RoomsOccupancyResponseDto> getById(Long id) {
        return roomsOccupancyRepository.findById(id).map(this::convertToDto);
    }

    public List<RoomsOccupancyResponseDto> listAll() {
        return roomsOccupancyRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<RoomsOccupancyResponseDto> listByPatient(Long patientId) {
        return roomsOccupancyRepository.findByPatientId(patientId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<RoomsOccupancyResponseDto> listByRoom(Long roomId) {
        return roomsOccupancyRepository.findByRoomId(roomId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private RoomsOccupancyResponseDto convertToDto(RoomsOccupancyEntity entity) {
        return new RoomsOccupancyResponseDto(
                entity.getId(),
                entity.getPatient().getName(),
                entity.getRoom().getRoomNumber(),
                entity.getStatus(),
                entity.getCheckInDate(),
                entity.getCheckOutDate(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}