package br.eng.campoy.sghssbackend.domain.stock.dto;

import lombok.Data;

@Data
public class StockDto {
    private Long serviceUnitId;
    private String name;
    private Integer quantity;
    private Integer minimumQuantity;
}