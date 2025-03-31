package br.eng.campoy.sghssbackend.domain.hospitalizationreport;

import br.eng.campoy.sghssbackend.domain.hospitalizationreport.dto.HospitalizationReportDto;
import br.eng.campoy.sghssbackend.domain.hospitalizationreport.dto.HospitalizationReportResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador para gerenciamento de relatórios de internação.
 */
@RestController
@RequestMapping("/hospitalization-reports")
@Tag(name = "Relatórios de Internação", description = "Gerenciamento de relatórios de internação hospitalar")
public class HospitalizationReportController {

    private final HospitalizationReport hospitalizationReport;

    public HospitalizationReportController(HospitalizationReport hospitalizationReport) {
        this.hospitalizationReport = hospitalizationReport;
    }

    /**
     * Cria um novo relatório de internação.
     *
     * @param dto Dados do relatório de internação a ser criado.
     * @return Relatório de internação criado.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL')")
    @Operation(
            summary = "Criar um relatório de internação",
            description = "Cria um novo relatório de internação no sistema. Apenas administradores e profissionais de saúde podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Relatório criado com sucesso",
                            content = @Content(schema = @Schema(implementation = HospitalizationReportResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    public ResponseEntity<HospitalizationReportResponseDto> createReport(@RequestBody HospitalizationReportDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hospitalizationReport.createReport(dto));
    }

    /**
     * Obtém os detalhes de um relatório de internação pelo ID.
     *
     * @param id ID do relatório de internação.
     * @return Detalhes do relatório de internação.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL')")
    @Operation(
            summary = "Buscar relatório de internação por ID",
            description = "Retorna os detalhes de um relatório de internação específico cadastrado no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Relatório encontrado",
                            content = @Content(schema = @Schema(implementation = HospitalizationReportResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Relatório não encontrado")
            }
    )
    public ResponseEntity<HospitalizationReportResponseDto> getReportById(@PathVariable Long id) {
        return hospitalizationReport.getReportById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista todos os relatórios de internação associados a uma internação específica.
     *
     * @param hospitalizationId ID da internação.
     * @return Lista de relatórios de internação associados.
     */
    @GetMapping("/hospitalization/{hospitalizationId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL')")
    @Operation(
            summary = "Listar relatórios por internação",
            description = "Retorna todos os relatórios de internação associados a uma internação específica.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de relatórios encontrados",
                            content = @Content(schema = @Schema(implementation = HospitalizationReportResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Internação não encontrada")
            }
    )
    public ResponseEntity<List<HospitalizationReportResponseDto>> listReportsByHospitalization(@PathVariable Long hospitalizationId) {
        return ResponseEntity.ok(hospitalizationReport.listReportsByHospitalization(hospitalizationId));
    }

    /**
     * Atualiza um relatório de internação existente.
     *
     * @param id  ID do relatório de internação a ser atualizado.
     * @param dto Dados atualizados do relatório de internação.
     * @return Relatório de internação atualizado.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL')")
    @Operation(
            summary = "Atualizar um relatório de internação",
            description = "Atualiza os dados de um relatório de internação. Apenas administradores e profissionais de saúde podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Relatório atualizado com sucesso",
                            content = @Content(schema = @Schema(implementation = HospitalizationReportResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Relatório não encontrado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<HospitalizationReportResponseDto> updateReport(@PathVariable Long id, @RequestBody HospitalizationReportDto dto) {
        return ResponseEntity.ok(hospitalizationReport.updateReport(id, dto));
    }

    /**
     * Exclui um relatório de internação do sistema.
     *
     * @param id ID do relatório de internação a ser excluído.
     * @return Status 204 (Sem conteúdo).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Excluir um relatório de internação",
            description = "Exclui um relatório de internação cadastrado no sistema. Apenas administradores podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Relatório excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Relatório não encontrado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        hospitalizationReport.deleteReport(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista todos os relatórios de internação dentro de um intervalo de datas.
     *
     * @param start Data de início do intervalo.
     * @param end   Data de fim do intervalo.
     * @return Lista de relatórios de internação no período especificado.
     */
    @GetMapping("/date-range")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL')")
    @Operation(
            summary = "Listar relatórios por intervalo de datas",
            description = "Retorna todos os relatórios de internação dentro de um intervalo de datas especificado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de relatórios encontrados",
                            content = @Content(schema = @Schema(implementation = HospitalizationReportResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Formato de data inválido")
            }
    )
    public ResponseEntity<List<HospitalizationReportResponseDto>> listReportsByDate(
            @RequestParam("start") String start,
            @RequestParam("end") String end) {
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);
        return ResponseEntity.ok(hospitalizationReport.listReportsByDate(startDate, endDate));
    }
}