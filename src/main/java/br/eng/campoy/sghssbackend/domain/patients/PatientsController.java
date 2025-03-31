package br.eng.campoy.sghssbackend.domain.patients;

import br.eng.campoy.sghssbackend.domain.patients.dto.PatientsDto;
import br.eng.campoy.sghssbackend.domain.users.dto.UsersPatientsDto;
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
 * Controlador para gerenciamento de pacientes.
 */
@RestController
@RequestMapping("/patients")
@Tag(name = "Pacientes", description = "Gerenciamento dos pacientes cadastrados no sistema")
public class PatientsController {

    private final Patients patients;

    public PatientsController(Patients patients) {
        this.patients = patients;
    }

    /**
     * Cria um novo paciente no sistema.
     *
     * @param dto Dados do paciente a ser criado.
     * @return Paciente criado.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFISSIONAL')")
    @Operation(
            summary = "Criar um paciente",
            description = "Cria um novo paciente no sistema. Apenas administradores e profissionais de saúde podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Paciente criado com sucesso",
                            content = @Content(schema = @Schema(implementation = UsersPatientsDto.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    public ResponseEntity<UsersPatientsDto> createPatient(@RequestBody PatientsDto dto) {
        UsersPatientsDto patient = patients.createPatient(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }

    /**
     * Obtém os detalhes de um paciente pelo ID.
     *
     * @param id ID do paciente.
     * @return Detalhes do paciente.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFISSIONAL') or (hasRole('PACIENTE') and principal.patientId == #id)")
    @Operation(
            summary = "Buscar paciente por ID",
            description = "Retorna os detalhes de um paciente específico. Administradores e profissionais podem buscar qualquer paciente, enquanto pacientes só podem buscar seus próprios dados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Paciente encontrado",
                            content = @Content(schema = @Schema(implementation = UsersPatientsDto.class))),
                    @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<UsersPatientsDto> getPatientById(@PathVariable Long id) {
        UsersPatientsDto patient = patients.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

    /**
     * Atualiza os dados de um paciente.
     *
     * @param id  ID do paciente a ser atualizado.
     * @param dto Dados do paciente atualizados.
     * @return Paciente atualizado.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFISSIONAL') or (hasRole('PACIENTE') and principal.patientId == #id)")
    @Operation(
            summary = "Atualizar paciente",
            description = "Atualiza os dados de um paciente. Administradores e profissionais podem atualizar qualquer paciente, enquanto pacientes só podem atualizar seus próprios dados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Paciente atualizado com sucesso",
                            content = @Content(schema = @Schema(implementation = UsersPatientsDto.class))),
                    @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<UsersPatientsDto> updatePatient(@PathVariable Long id, @RequestBody PatientsDto dto) {
        UsersPatientsDto updatedPatient = patients.updatePatient(id, dto);
        return ResponseEntity.ok(updatedPatient);
    }

    /**
     * Exclui um paciente do sistema.
     *
     * @param id ID do paciente a ser excluído.
     * @return Status 204 (Sem conteúdo).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Excluir paciente",
            description = "Exclui um paciente do sistema. Apenas administradores podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Paciente excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patients.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista todos os pacientes cadastrados no sistema.
     *
     * @return Lista de pacientes.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFISSIONAL')")
    @Operation(
            summary = "Listar todos os pacientes",
            description = "Retorna uma lista de todos os pacientes cadastrados no sistema. Apenas administradores e profissionais podem visualizar.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de pacientes",
                            content = @Content(schema = @Schema(implementation = UsersPatientsDto.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<List<UsersPatientsDto>> listPatients() {
        List<UsersPatientsDto> patients = this.patients.listPatients();
        return ResponseEntity.ok(patients);
    }
}