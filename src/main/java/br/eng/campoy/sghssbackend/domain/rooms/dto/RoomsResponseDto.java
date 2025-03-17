package br.eng.campoy.sghssbackend.domain.rooms.dto;

import br.eng.campoy.sghssbackend.domain.rooms.ValueObject.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomsResponseDto {
    private Long id;
    private String serviceUnitName;
    private String roomNumber;
    private Integer capacity;
    private RoomStatus status;
}