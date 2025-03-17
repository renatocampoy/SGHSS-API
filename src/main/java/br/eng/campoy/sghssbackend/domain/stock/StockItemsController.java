package br.eng.campoy.sghssbackend.domain.stock;

import br.eng.campoy.sghssbackend.domain.stock.dto.StockItemDto;
import br.eng.campoy.sghssbackend.domain.stock.dto.StockItemResponseDto;
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
 * Controlador para gerenciamento de itens do estoque.
 */
@RestController
@RequestMapping("/stock/items")
@Tag(name = "Itens do Estoque", description = "Gerenciamento dos itens armazenados no estoque")
public class StockItemsController {

    private final StockItemsService stockItemsService;

    public StockItemsController(StockItemsService stockItemsService) {
        this.stockItemsService = stockItemsService;
    }

    /**
     * Adiciona um novo item ao estoque.
     *
     * @param dto Dados do item de estoque a ser adicionado.
     * @return Item de estoque criado.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Adicionar um item ao estoque",
            description = "Adiciona um novo item ao estoque. Apenas administradores podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Item de estoque adicionado com sucesso",
                            content = @Content(schema = @Schema(implementation = StockItemResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    public ResponseEntity<StockItemResponseDto> addStockItem(@RequestBody StockItemDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stockItemsService.addStockItem(dto));
    }

    /**
     * Obtém os detalhes de um item do estoque pelo ID.
     *
     * @param id ID do item de estoque.
     * @return Detalhes do item de estoque.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar item de estoque por ID",
            description = "Retorna os detalhes de um item específico do estoque.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item encontrado",
                            content = @Content(schema = @Schema(implementation = StockItemResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Item não encontrado")
            }
    )
    public ResponseEntity<StockItemResponseDto> getStockItemById(@PathVariable Long id) {
        return stockItemsService.getStockItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista todos os itens de um estoque específico.
     *
     * @param stockId ID do estoque.
     * @return Lista de itens do estoque.
     */
    @GetMapping("/stock/{stockId}")
    @Operation(
            summary = "Listar itens do estoque",
            description = "Retorna todos os itens armazenados em um estoque específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de itens encontrados",
                            content = @Content(schema = @Schema(implementation = StockItemResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Estoque não encontrado")
            }
    )
    public ResponseEntity<List<StockItemResponseDto>> listStockItems(@PathVariable Long stockId) {
        return ResponseEntity.ok(stockItemsService.listStockItems(stockId));
    }
}