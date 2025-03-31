package br.eng.campoy.sghssbackend.domain.roomsoccupancy;

import br.eng.campoy.sghssbackend.domain.roomsoccupancy.dto.RoomsOccupancyDto;
import br.eng.campoy.sghssbackend.domain.roomsoccupancy.dto.RoomsOccupancyResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gerenciamento da ocupação de quartos.
 */
@RestController
@RequestMapping("/rooms-occupancy")
@Tag(name = "Ocupação de Quartos", description = "Gerenciamento da ocupação e liberação de quartos no sistema hospitalar")
public class RoomsOccupancyController {

    private final RoomsOccupancy roomsOccupancy;

    public RoomsOccupancyController(RoomsOccupancy roomsOccupancy) {
        this.roomsOccupancy = roomsOccupancy;
    }

    /**
     * Registra a ocupação de um quarto por um paciente.
     *
     * @param dto Dados da ocupação do quarto.
     * @return Registro da ocupação.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL')")
    @Operation(
            summary = "Registrar ocupação de quarto",
            description = "Registra a ocupação de um quarto por um paciente. Apenas administradores e profissionais podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ocupação registrada com sucesso",
                            content = @Content(schema = @Schema(implementation = RoomsOccupancyResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    public ResponseEntity<RoomsOccupancyResponseDto> register(@RequestBody RoomsOccupancyDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomsOccupancy.registerOccupancy(dto));
    }

    /**
     * Libera um quarto ocupado.
     *
     * @param id ID da ocupação a ser liberada.
     * @return Status 204 (Sem conteúdo).
     */
    @PatchMapping("/{id}/release")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL')")
    @Operation(
            summary = "Liberar um quarto ocupado",
            description = "Libera um quarto ocupado. Apenas administradores e profissionais podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Quarto liberado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Ocupação não encontrada"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<Void> release(@PathVariable Long id) {
        roomsOccupancy.releaseRoom(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtém os detalhes de uma ocupação específica pelo ID.
     *
     * @param id ID da ocupação.
     * @return Detalhes da ocupação.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL')")
    @Operation(
            summary = "Buscar ocupação por ID",
            description = "Retorna os detalhes de uma ocupação específica de um quarto.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ocupação encontrada",
                            content = @Content(schema = @Schema(implementation = RoomsOccupancyResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Ocupação não encontrada"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<RoomsOccupancyResponseDto> getById(@PathVariable Long id) {
        return roomsOccupancy.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista todas as ocupações de quartos registradas no sistema.
     *
     * @return Lista de ocupações.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL')")
    @Operation(
            summary = "Listar todas as ocupações de quartos",
            description = "Retorna uma lista de todas as ocupações de quartos registradas no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de ocupações de quartos",
                            content = @Content(schema = @Schema(implementation = RoomsOccupancyResponseDto.class)))
            }
    )
    public ResponseEntity<List<RoomsOccupancyResponseDto>> listAll() {
        return ResponseEntity.ok(roomsOccupancy.listAll());
    }

    /**
     * Lista todas as ocupações de quartos associadas a um paciente específico.
     *
     * @param patientId ID do paciente.
     * @return Lista de ocupações do paciente.
     */
    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL') or (hasRole('PACIENTE') and principal.id == #patientId)")
    @Operation(
            summary = "Listar ocupações por paciente",
            description = "Retorna todas as ocupações de quartos associadas a um paciente específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de ocupações do paciente",
                            content = @Content(schema = @Schema(implementation = RoomsOccupancyResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<List<RoomsOccupancyResponseDto>> listByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(roomsOccupancy.listByPatient(patientId));
    }

    /**
     * Lista todas as ocupações de um quarto específico.
     *
     * @param roomId ID do quarto.
     * @return Lista de ocupações do quarto.
     */
    @GetMapping("/room/{roomId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL')")
    @Operation(
            summary = "Listar ocupações por quarto",
            description = "Retorna todas as ocupações de um quarto específico no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de ocupações do quarto",
                            content = @Content(schema = @Schema(implementation = RoomsOccupancyResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Quarto não encontrado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<List<RoomsOccupancyResponseDto>> listByRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(roomsOccupancy.listByRoom(roomId));
    }
}