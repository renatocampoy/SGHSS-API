package br.eng.campoy.sghssbackend.domain.stock;

import br.eng.campoy.sghssbackend.domain.stock.dto.StockDto;
import br.eng.campoy.sghssbackend.domain.stock.dto.StockResponseDto;
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
 * Controlador para gerenciamento de estoques.
 */
@RestController
@RequestMapping("/stock")
@Tag(name = "Estoque", description = "Gerenciamento de estoques de medicamentos e insumos")
public class StockController {

    private final Stock stock;

    public StockController(Stock stock) {
        this.stock = stock;
    }

    /**
     * Cria um novo estoque.
     *
     * @param dto Dados do estoque a ser criado.
     * @return Estoque criado.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Criar um estoque",
            description = "Cria um novo estoque de medicamentos ou insumos. Apenas administradores podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Estoque criado com sucesso",
                            content = @Content(schema = @Schema(implementation = StockResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    public ResponseEntity<StockResponseDto> createStock(@RequestBody StockDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stock.createStock(dto));
    }

    /**
     * Atualiza os dados de um estoque existente.
     *
     * @param id  ID do estoque a ser atualizado.
     * @param dto Dados atualizados do estoque.
     * @return Estoque atualizado.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Atualizar estoque",
            description = "Atualiza os dados de um estoque específico. Apenas administradores podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Estoque atualizado com sucesso",
                            content = @Content(schema = @Schema(implementation = StockResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Estoque não encontrado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<StockResponseDto> updateStock(@PathVariable Long id, @RequestBody StockDto dto) {
        return ResponseEntity.ok(stock.updateStock(id, dto));
    }

    /**
     * Exclui um estoque do sistema.
     *
     * @param id ID do estoque a ser excluído.
     * @return Status 204 (Sem conteúdo).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Excluir estoque",
            description = "Exclui um estoque do sistema. Apenas administradores podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Estoque excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Estoque não encontrado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stock.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtém os detalhes de um estoque específico pelo ID.
     *
     * @param id ID do estoque.
     * @return Detalhes do estoque.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar estoque por ID",
            description = "Retorna os detalhes de um estoque específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Estoque encontrado",
                            content = @Content(schema = @Schema(implementation = StockResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Estoque não encontrado")
            }
    )
    public ResponseEntity<StockResponseDto> getStockById(@PathVariable Long id) {
        return stock.getStockById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista todos os estoques cadastrados no sistema.
     *
     * @return Lista de estoques.
     */
    @GetMapping
    @Operation(
            summary = "Listar todos os estoques",
            description = "Retorna uma lista de todos os estoques cadastrados no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de estoques",
                            content = @Content(schema = @Schema(implementation = StockResponseDto.class)))
            }
    )
    public ResponseEntity<List<StockResponseDto>> listStock() {
        return ResponseEntity.ok(stock.listStock());
    }

    /**
     * Lista estoques de uma unidade de serviço específica.
     *
     * @param serviceUnitId ID da unidade de serviço.
     * @return Lista de estoques vinculados à unidade de serviço.
     */
    @GetMapping("/service-unit/{serviceUnitId}")
    @Operation(
            summary = "Listar estoques por unidade de serviço",
            description = "Retorna todos os estoques cadastrados para uma unidade de serviço específica.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de estoques",
                            content = @Content(schema = @Schema(implementation = StockResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Unidade de serviço não encontrada")
            }
    )
    public ResponseEntity<List<StockResponseDto>> listByServiceUnit(@PathVariable Long serviceUnitId) {
        return ResponseEntity.ok(stock.listByServiceUnit(serviceUnitId));
    }
}