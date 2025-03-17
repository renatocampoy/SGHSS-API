package br.eng.campoy.sghssbackend.domain.roomsoccupancy.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoomsOccupancyDto {
    private Long patientId;
    private Long roomId;
    private LocalDateTime checkInDate;
}