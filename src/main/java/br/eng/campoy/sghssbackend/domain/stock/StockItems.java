package br.eng.campoy.sghssbackend.domain.stock;

import br.eng.campoy.sghssbackend.domain.stock.dto.StockItemDto;
import br.eng.campoy.sghssbackend.domain.stock.dto.StockItemResponseDto;
import br.eng.campoy.sghssbackend.domain.stock.exception.StockItemException;
import br.eng.campoy.sghssbackend.domain.stock.ValueObject.StockTransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockItems {

    private final StockItemsRepository stockItemsRepository;
    private final StockRepository stockRepository;

    @Autowired
    public StockItems(StockItemsRepository stockItemsRepository, StockRepository stockRepository) {
        this.stockItemsRepository = stockItemsRepository;
        this.stockRepository = stockRepository;
    }

    public StockItemResponseDto addStockItem(StockItemDto dto) {
        StockEntity stock = stockRepository.findById(dto.getStockId())
                .orElseThrow(() -> new StockItemException("Estoque não encontrado."));

        if (dto.getTransactionType() == StockTransactionType.SAIDA && stock.getQuantity() < dto.getQuantity()) {
            throw new StockItemException("Estoque insuficiente para a retirada.");
        }

        StockItemsEntity stockItem = new StockItemsEntity();
        stockItem.setStock(stock);
        stockItem.setItemName(dto.getItemName());
        stockItem.setTransactionType(dto.getTransactionType());
        stockItem.setQuantity(dto.getQuantity());
        stockItem.setTransactionDate(dto.getTransactionDate());

        if (dto.getTransactionType() == StockTransactionType.ENTRADA) {
            stock.setQuantity(stock.getQuantity() + dto.getQuantity());
        } else {
            stock.setQuantity(stock.getQuantity() - dto.getQuantity());
        }

        stockRepository.save(stock);
        return convertToDto(stockItemsRepository.save(stockItem));
    }

    public Optional<StockItemResponseDto> getStockItemById(Long id) {
        return stockItemsRepository.findById(id).map(this::convertToDto);
    }

    public List<StockItemResponseDto> listStockItems(Long stockId) {
        return stockItemsRepository.findByStockId(stockId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private StockItemResponseDto convertToDto(StockItemsEntity entity) {
        return new StockItemResponseDto(
                entity.getId(),
                entity.getStock().getName(),
                entity.getItemName(),
                entity.getTransactionType(),
                entity.getQuantity(),
                entity.getTransactionDate()
        );
    }
}