package br.eng.campoy.sghssbackend.domain.roomsoccupancy.dto;

import br.eng.campoy.sghssbackend.domain.roomsoccupancy.ValueObject.RoomsOccupancyStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RoomsOccupancyResponseDto {
    private Long id;
    private String patientName;
    private String roomNumber;
    private RoomsOccupancyStatus status;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}