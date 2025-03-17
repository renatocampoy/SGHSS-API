package br.eng.campoy.sghssbackend.domain.stock.dto;

import br.eng.campoy.sghssbackend.domain.stock.ValueObject.StockTransactionType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockItemDto {
    private Long stockId;
    private String itemName;
    private StockTransactionType transactionType;
    private Integer quantity;
    private LocalDateTime transactionDate;
}