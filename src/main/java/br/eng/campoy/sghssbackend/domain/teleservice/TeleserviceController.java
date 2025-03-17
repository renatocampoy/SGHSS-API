package br.eng.campoy.sghssbackend.domain.teleservice;

import br.eng.campoy.sghssbackend.domain.teleservice.ValueObject.TeleserviceStatus;
import br.eng.campoy.sghssbackend.domain.teleservice.dto.TeleserviceDto;
import br.eng.campoy.sghssbackend.domain.teleservice.dto.TeleserviceResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador para gerenciamento de teleatendimentos.
 */
@RestController
@RequestMapping("/teleservice")
@Tag(name = "Teleatendimentos", description = "Gerenciamento de teleatendimentos entre profissionais e pacientes")
public class TeleserviceController {

    private final TeleserviceService teleserviceService;

    public TeleserviceController(TeleserviceService teleserviceService) {
        this.teleserviceService = teleserviceService;
    }

    /**
     * Cria um novo teleatendimento.
     *
     * @param dto Dados do teleatendimento a ser criado.
     * @return Teleatendimento criado.
     */
    @PostMapping
    @Operation(
            summary = "Criar um teleatendimento",
            description = "Cria um novo teleatendimento entre um profissional e um paciente.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Teleatendimento criado com sucesso",
                            content = @Content(schema = @Schema(implementation = TeleserviceResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    public ResponseEntity<TeleserviceResponseDto> createTeleservice(@RequestBody TeleserviceDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teleserviceService.createTeleservice(dto));
    }

    /**
     * Obtém os detalhes de um teleatendimento pelo ID.
     *
     * @param id ID do teleatendimento.
     * @return Detalhes do teleatendimento.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar teleatendimento por ID",
            description = "Retorna os detalhes de um teleatendimento específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Teleatendimento encontrado",
                            content = @Content(schema = @Schema(implementation = TeleserviceResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Teleatendimento não encontrado")
            }
    )
    public ResponseEntity<TeleserviceResponseDto> getTeleserviceById(@PathVariable Long id) {
        return teleserviceService.getTeleserviceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista teleatendimentos de um paciente específico.
     *
     * @param patientId ID do paciente.
     * @return Lista de teleatendimentos do paciente.
     */
    @GetMapping("/patient/{patientId}")
    @Operation(
            summary = "Listar teleatendimentos por paciente",
            description = "Retorna todos os teleatendimentos associados a um paciente específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de teleatendimentos",
                            content = @Content(schema = @Schema(implementation = TeleserviceResponseDto.class)))
            }
    )
    public ResponseEntity<List<TeleserviceResponseDto>> listByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(teleserviceService.listByPatient(patientId));
    }

    /**
     * Lista teleatendimentos de um profissional específico.
     *
     * @param professionalId ID do profissional.
     * @return Lista de teleatendimentos do profissional.
     */
    @GetMapping("/professional/{professionalId}")
    @Operation(
            summary = "Listar teleatendimentos por profissional",
            description = "Retorna todos os teleatendimentos associados a um profissional específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de teleatendimentos",
                            content = @Content(schema = @Schema(implementation = TeleserviceResponseDto.class)))
            }
    )
    public ResponseEntity<List<TeleserviceResponseDto>> listByProfessional(@PathVariable Long professionalId) {
        return ResponseEntity.ok(teleserviceService.listByProfessional(professionalId));
    }

    /**
     * Atualiza o status de um teleatendimento.
     *
     * @param id     ID do teleatendimento.
     * @param status Novo status do teleatendimento.
     * @return Teleatendimento atualizado.
     */
    @PutMapping("/{id}/status")
    @Operation(
            summary = "Atualizar status do teleatendimento",
            description = "Atualiza o status de um teleatendimento específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso",
                            content = @Content(schema = @Schema(implementation = TeleserviceResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Teleatendimento não encontrado")
            }
    )
    public ResponseEntity<TeleserviceResponseDto> updateTeleserviceStatus(
            @PathVariable Long id,
            @RequestParam TeleserviceStatus status) {
        return ResponseEntity.ok(teleserviceService.updateTeleserviceStatus(id, status));
    }

    /**
     * Cancela um teleatendimento.
     *
     * @param id ID do teleatendimento a ser cancelado.
     * @return Status 204 (Sem conteúdo).
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Cancelar teleatendimento",
            description = "Cancela um teleatendimento existente.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Teleatendimento cancelado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Teleatendimento não encontrado")
            }
    )
    public ResponseEntity<Void> cancelTeleservice(@PathVariable Long id) {
        teleserviceService.cancelTeleservice(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista teleatendimentos em um intervalo de datas.
     *
     * @param start Data e hora de início no formato ISO-8601 (exemplo: "2025-03-17T08:00").
     * @param end   Data e hora de término no formato ISO-8601 (exemplo: "2025-03-17T18:00").
     * @return Lista de teleatendimentos dentro do período especificado.
     */
    @GetMapping("/date-range")
    @Operation(
            summary = "Listar teleatendimentos por período",
            description = "Retorna todos os teleatendimentos dentro de um intervalo de datas.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de teleatendimentos",
                            content = @Content(schema = @Schema(implementation = TeleserviceResponseDto.class)))
            }
    )
    public ResponseEntity<List<TeleserviceResponseDto>> listByDateRange(
            @RequestParam("start") String start,
            @RequestParam("end") String end) {
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);
        return ResponseEntity.ok(teleserviceService.listByDateRange(startDate, endDate));
    }
}