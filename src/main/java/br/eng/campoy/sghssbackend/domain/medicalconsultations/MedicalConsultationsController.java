package br.eng.campoy.sghssbackend.domain.medicalconsultations;

import br.eng.campoy.sghssbackend.domain.medicalconsultations.dto.MedicalConsultationsDto;
import br.eng.campoy.sghssbackend.domain.medicalconsultations.dto.MedicalConsultationsResponseDto;
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
 * Controlador para gerenciamento de consultas médicas.
 */
@RestController
@RequestMapping("/medical-consultations")
@Tag(name = "Consultas Médicas", description = "Gerenciamento de consultas médicas realizadas no sistema")
public class MedicalConsultationsController {

    private final MedicalConsultationsService medicalConsultationService;

    public MedicalConsultationsController(MedicalConsultationsService medicalConsultationService) {
        this.medicalConsultationService = medicalConsultationService;
    }

    /**
     * Cria uma nova consulta médica.
     *
     * @param dto Dados da consulta médica a ser criada.
     * @return Consulta médica criada.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL')")
    @Operation(
            summary = "Criar uma consulta médica",
            description = "Cria uma nova consulta médica no sistema. Apenas administradores e profissionais de saúde podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Consulta criada com sucesso",
                            content = @Content(schema = @Schema(implementation = MedicalConsultationsResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    public ResponseEntity<MedicalConsultationsResponseDto> create(@RequestBody MedicalConsultationsDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicalConsultationService.createMedicalConsultation(dto));
    }

    /**
     * Atualiza uma consulta médica existente.
     *
     * @param id  ID da consulta a ser atualizada.
     * @param dto Dados da consulta médica atualizados.
     * @return Consulta médica atualizada.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL')")
    @Operation(
            summary = "Atualizar uma consulta médica",
            description = "Atualiza os dados de uma consulta médica. Apenas administradores e profissionais de saúde podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Consulta atualizada com sucesso",
                            content = @Content(schema = @Schema(implementation = MedicalConsultationsResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Consulta não encontrada"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<MedicalConsultationsResponseDto> update(@PathVariable Long id, @RequestBody MedicalConsultationsDto dto) {
        return ResponseEntity.ok(medicalConsultationService.updateMedicalConsultation(id, dto));
    }

    /**
     * Cancela uma consulta médica existente.
     *
     * @param id ID da consulta a ser cancelada.
     * @return Status 204 (Sem conteúdo).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL')")
    @Operation(
            summary = "Cancelar uma consulta médica",
            description = "Cancela uma consulta médica cadastrada no sistema. Apenas administradores e profissionais de saúde podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Consulta cancelada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Consulta não encontrada"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        medicalConsultationService.cancelMedicalConsultation(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtém os detalhes de uma consulta médica pelo ID.
     *
     * @param id ID da consulta médica.
     * @return Detalhes da consulta médica.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar consulta médica por ID",
            description = "Retorna os detalhes de uma consulta médica específica cadastrada no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Consulta encontrada",
                            content = @Content(schema = @Schema(implementation = MedicalConsultationsResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Consulta não encontrada")
            }
    )
    public ResponseEntity<MedicalConsultationsResponseDto> getById(@PathVariable Long id) {
        return medicalConsultationService.getMedicalConsultationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista todas as consultas médicas cadastradas no sistema.
     *
     * @return Lista de consultas médicas.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL')")
    @Operation(
            summary = "Listar todas as consultas médicas",
            description = "Retorna uma lista de todas as consultas médicas cadastradas no sistema. Apenas administradores e profissionais podem visualizar.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de consultas",
                            content = @Content(schema = @Schema(implementation = MedicalConsultationsResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<List<MedicalConsultationsResponseDto>> listAll() {
        return ResponseEntity.ok(medicalConsultationService.listAllMedicalConsultations());
    }

    /**
     * Lista todas as consultas médicas associadas a um paciente específico.
     *
     * @param patientId ID do paciente.
     * @return Lista de consultas do paciente.
     */
    @GetMapping("/patient/{patientId}")
    @Operation(
            summary = "Listar consultas por paciente",
            description = "Retorna todas as consultas médicas associadas a um paciente específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de consultas encontradas",
                            content = @Content(schema = @Schema(implementation = MedicalConsultationsResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
            }
    )
    public ResponseEntity<List<MedicalConsultationsResponseDto>> listByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(medicalConsultationService.listByPatient(patientId));
    }

    /**
     * Lista todas as consultas médicas associadas a um profissional específico.
     *
     * @param professionalId ID do profissional de saúde.
     * @return Lista de consultas do profissional.
     */
    @GetMapping("/professional/{professionalId}")
    @Operation(
            summary = "Listar consultas por profissional",
            description = "Retorna todas as consultas médicas associadas a um profissional específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de consultas encontradas",
                            content = @Content(schema = @Schema(implementation = MedicalConsultationsResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
            }
    )
    public ResponseEntity<List<MedicalConsultationsResponseDto>> listByProfessional(@PathVariable Long professionalId) {
        return ResponseEntity.ok(medicalConsultationService.listByProfessional(professionalId));
    }
}