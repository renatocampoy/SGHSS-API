package br.eng.campoy.sghssbackend.domain.rooms.dto;

import br.eng.campoy.sghssbackend.domain.rooms.ValueObject.RoomStatus;
import lombok.Data;

@Data
public class RoomsDto {
    private Long serviceUnitId;
    private String roomNumber;
    private Integer capacity;
    private RoomStatus status;
}