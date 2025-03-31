package br.eng.campoy.sghssbackend.domain.medicalprescription;

import br.eng.campoy.sghssbackend.domain.medicalprescription.dto.MedicalPrescriptionDto;
import br.eng.campoy.sghssbackend.domain.medicalprescription.dto.MedicalPrescriptionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gerenciamento de prescrições médicas.
 */
@RestController
@RequestMapping("/medical-prescriptions")
@Tag(name = "Prescrições Médicas", description = "Gerenciamento de prescrições médicas associadas a consultas")
public class MedicalPrescriptionController {

    private final MedicalPrescription medicalPrescription;

    public MedicalPrescriptionController(MedicalPrescription medicalPrescription) {
        this.medicalPrescription = medicalPrescription;
    }

    /**
     * Cria uma nova prescrição médica.
     *
     * @param dto Dados da prescrição médica a ser criada.
     * @return Prescrição médica criada.
     */
    @PostMapping
    @Operation(
            summary = "Criar uma prescrição médica",
            description = "Cria uma nova prescrição médica associada a uma consulta.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Prescrição criada com sucesso",
                            content = @Content(schema = @Schema(implementation = MedicalPrescriptionResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    public ResponseEntity<MedicalPrescriptionResponseDto> createPrescription(@RequestBody MedicalPrescriptionDto dto) {
        return ResponseEntity.ok(medicalPrescription.createPrescription(dto));
    }

    /**
     * Lista todas as prescrições médicas associadas a uma consulta específica.
     *
     * @param consultationId ID da consulta.
     * @return Lista de prescrições médicas associadas à consulta.
     */
    @GetMapping("/consultation/{consultationId}")
    @Operation(
            summary = "Listar prescrições por consulta",
            description = "Retorna todas as prescrições médicas associadas a uma consulta específica.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de prescrições encontradas",
                            content = @Content(schema = @Schema(implementation = MedicalPrescriptionResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Consulta não encontrada")
            }
    )
    public ResponseEntity<List<MedicalPrescriptionResponseDto>> listByConsultation(@PathVariable Long consultationId) {
        return ResponseEntity.ok(medicalPrescription.listByConsultation(consultationId));
    }
}