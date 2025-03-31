package br.eng.campoy.sghssbackend.domain.serviceunit;

import br.eng.campoy.sghssbackend.domain.serviceunit.dto.ServiceUnitDto;
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
 * Controlador para gerenciamento de unidades de serviço.
 */
@RestController
@RequestMapping("/service-units")
@Tag(name = "Unidades de Serviço", description = "Gerenciamento das unidades de atendimento médico e hospitalar")
public class ServiceUnitController {

    private final ServiceUnit serviceUnit;

    public ServiceUnitController(ServiceUnit serviceUnit) {
        this.serviceUnit = serviceUnit;
    }

    /**
     * Cria uma nova unidade de serviço.
     *
     * @param dto Dados da unidade de serviço a ser criada.
     * @return Unidade de serviço criada.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Criar uma unidade de serviço",
            description = "Cria uma nova unidade de atendimento médico ou hospitalar. Apenas administradores podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Unidade de serviço criada com sucesso",
                            content = @Content(schema = @Schema(implementation = ServiceUnitDto.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    public ResponseEntity<ServiceUnitDto> createServiceUnit(@RequestBody ServiceUnitDto dto) {
        ServiceUnitDto serviceUnit = this.serviceUnit.createServiceUnit(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceUnit);
    }

    /**
     * Obtém os detalhes de uma unidade de serviço pelo ID.
     *
     * @param id ID da unidade de serviço.
     * @return Detalhes da unidade de serviço.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar unidade de serviço por ID",
            description = "Retorna os detalhes de uma unidade de serviço específica.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Unidade de serviço encontrada",
                            content = @Content(schema = @Schema(implementation = ServiceUnitDto.class))),
                    @ApiResponse(responseCode = "404", description = "Unidade de serviço não encontrada")
            }
    )
    public ResponseEntity<ServiceUnitDto> getServiceUnitById(@PathVariable Long id) {
        return serviceUnit.getServiceUnitById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Atualiza os dados de uma unidade de serviço existente.
     *
     * @param id  ID da unidade de serviço a ser atualizada.
     * @param dto Dados atualizados da unidade de serviço.
     * @return Unidade de serviço atualizada.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Atualizar unidade de serviço",
            description = "Atualiza os dados de uma unidade de atendimento médico ou hospitalar. Apenas administradores podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Unidade de serviço atualizada com sucesso",
                            content = @Content(schema = @Schema(implementation = ServiceUnitDto.class))),
                    @ApiResponse(responseCode = "404", description = "Unidade de serviço não encontrada"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<ServiceUnitDto> updateServiceUnit(@PathVariable Long id, @RequestBody ServiceUnitDto dto) {
        ServiceUnitDto updatedServiceUnit = serviceUnit.updateServiceUnit(id, dto);
        return ResponseEntity.ok(updatedServiceUnit);
    }

    /**
     * Exclui uma unidade de serviço do sistema.
     *
     * @param id ID da unidade de serviço a ser excluída.
     * @return Status 204 (Sem conteúdo).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Excluir unidade de serviço",
            description = "Exclui uma unidade de serviço cadastrada. Apenas administradores podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Unidade de serviço excluída com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Unidade de serviço não encontrada"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<Void> deleteServiceUnit(@PathVariable Long id) {
        serviceUnit.deleteServiceUnit(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista todas as unidades de serviço cadastradas no sistema.
     *
     * @return Lista de unidades de serviço.
     */
    @GetMapping
    @Operation(
            summary = "Listar todas as unidades de serviço",
            description = "Retorna uma lista de todas as unidades de serviço cadastradas no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de unidades de serviço",
                            content = @Content(schema = @Schema(implementation = ServiceUnitDto.class)))
            }
    )
    public ResponseEntity<List<ServiceUnitDto>> listServiceUnits() {
        List<ServiceUnitDto> serviceUnits = serviceUnit.listServiceUnits();
        return ResponseEntity.ok(serviceUnits);
    }
}