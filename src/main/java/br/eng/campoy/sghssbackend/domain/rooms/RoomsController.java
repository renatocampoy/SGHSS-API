package br.eng.campoy.sghssbackend.domain.rooms;

import br.eng.campoy.sghssbackend.domain.rooms.dto.RoomsDto;
import br.eng.campoy.sghssbackend.domain.rooms.dto.RoomsResponseDto;
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
 * Controlador para gerenciamento de quartos hospitalares.
 */
@RestController
@RequestMapping("/rooms")
@Tag(name = "Quartos", description = "Gerenciamento de quartos hospitalares disponíveis para ocupação")
public class RoomsController {

    private final Rooms rooms;

    public RoomsController(Rooms rooms) {
        this.rooms = rooms;
    }

    /**
     * Cria um novo quarto.
     *
     * @param dto Dados do quarto a ser criado.
     * @return Quarto criado.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Criar um quarto",
            description = "Cria um novo quarto hospitalar no sistema. Apenas administradores podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Quarto criado com sucesso",
                            content = @Content(schema = @Schema(implementation = RoomsResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    public ResponseEntity<RoomsResponseDto> createRoom(@RequestBody RoomsDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rooms.createRoom(dto));
    }

    /**
     * Atualiza os dados de um quarto existente.
     *
     * @param id  ID do quarto a ser atualizado.
     * @param dto Dados atualizados do quarto.
     * @return Quarto atualizado.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Atualizar um quarto",
            description = "Atualiza os dados de um quarto hospitalar. Apenas administradores podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Quarto atualizado com sucesso",
                            content = @Content(schema = @Schema(implementation = RoomsResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Quarto não encontrado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<RoomsResponseDto> updateRoom(@PathVariable Long id, @RequestBody RoomsDto dto) {
        return ResponseEntity.ok(rooms.updateRoom(id, dto));
    }

    /**
     * Exclui um quarto do sistema.
     *
     * @param id ID do quarto a ser excluído.
     * @return Status 204 (Sem conteúdo).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Excluir um quarto",
            description = "Exclui um quarto cadastrado no sistema. Apenas administradores podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Quarto excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Quarto não encontrado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        rooms.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtém os detalhes de um quarto específico pelo ID.
     *
     * @param id ID do quarto.
     * @return Detalhes do quarto.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar quarto por ID",
            description = "Retorna os detalhes de um quarto específico do sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Quarto encontrado",
                            content = @Content(schema = @Schema(implementation = RoomsResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Quarto não encontrado")
            }
    )
    public ResponseEntity<RoomsResponseDto> getRoomById(@PathVariable Long id) {
        return rooms.getRoomById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista todos os quartos cadastrados no sistema.
     *
     * @return Lista de quartos.
     */
    @GetMapping
    @Operation(
            summary = "Listar todos os quartos",
            description = "Retorna uma lista de todos os quartos hospitalares cadastrados no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de quartos",
                            content = @Content(schema = @Schema(implementation = RoomsResponseDto.class)))
            }
    )
    public ResponseEntity<List<RoomsResponseDto>> listRooms() {
        return ResponseEntity.ok(rooms.listRooms());
    }

    /**
     * Lista os quartos de uma unidade de serviço específica.
     *
     * @param serviceUnitId ID da unidade de serviço.
     * @return Lista de quartos vinculados à unidade de serviço.
     */
    @GetMapping("/service-unit/{serviceUnitId}")
    @Operation(
            summary = "Listar quartos por unidade de serviço",
            description = "Retorna todos os quartos cadastrados para uma unidade de serviço específica.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de quartos",
                            content = @Content(schema = @Schema(implementation = RoomsResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Unidade de serviço não encontrada")
            }
    )
    public ResponseEntity<List<RoomsResponseDto>> listByServiceUnit(@PathVariable Long serviceUnitId) {
        return ResponseEntity.ok(rooms.listByServiceUnit(serviceUnitId));
    }
}