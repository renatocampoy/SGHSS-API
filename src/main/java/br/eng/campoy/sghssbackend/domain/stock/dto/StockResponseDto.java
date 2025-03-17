package br.eng.campoy.sghssbackend.domain.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockResponseDto {
    private Long id;
    private String serviceUnitName;
    private String name;
    private Integer quantity;
    private Integer minimumQuantity;
}