package br.eng.campoy.sghssbackend.domain.stock.dto;

import br.eng.campoy.sghssbackend.domain.stock.ValueObject.StockTransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StockItemResponseDto {
    private Long id;
    private String stockName;
    private String itemName;
    private StockTransactionType transactionType;
    private Integer quantity;
    private LocalDateTime transactionDate;
}