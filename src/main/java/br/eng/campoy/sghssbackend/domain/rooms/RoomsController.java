package br.eng.campoy.sghssbackend.domain.rooms;

import br.eng.campoy.sghssbackend.domain.rooms.dto.RoomsDto;
import br.eng.campoy.sghssbackend.domain.rooms.dto.RoomsResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomsController {

    private final RoomsService roomsService;

    public RoomsController(RoomsService roomsService) {
        this.roomsService = roomsService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomsResponseDto> createRoom(@RequestBody RoomsDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomsService.createRoom(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomsResponseDto> updateRoom(@PathVariable Long id, @RequestBody RoomsDto dto) {
        return ResponseEntity.ok(roomsService.updateRoom(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomsService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomsResponseDto> getRoomById(@PathVariable Long id) {
        return roomsService.getRoomById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<RoomsResponseDto>> listRooms() {
        return ResponseEntity.ok(roomsService.listRooms());
    }

    @GetMapping("/service-unit/{serviceUnitId}")
    public ResponseEntity<List<RoomsResponseDto>> listByServiceUnit(@PathVariable Long serviceUnitId) {
        return ResponseEntity.ok(roomsService.listByServiceUnit(serviceUnitId));
    }
}