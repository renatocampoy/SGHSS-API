package br.eng.campoy.sghssbackend.domain.stock;

import br.eng.campoy.sghssbackend.domain.serviceunit.ServiceUnitEntity;
import br.eng.campoy.sghssbackend.domain.serviceunit.ServiceUnitRepository;
import br.eng.campoy.sghssbackend.domain.stock.dto.StockDto;
import br.eng.campoy.sghssbackend.domain.stock.dto.StockResponseDto;
import br.eng.campoy.sghssbackend.domain.stock.exception.StockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Stock {

    private final StockRepository stockRepository;
    private final ServiceUnitRepository serviceUnitRepository;

    @Autowired
    public Stock(StockRepository stockRepository, ServiceUnitRepository serviceUnitRepository) {
        this.stockRepository = stockRepository;
        this.serviceUnitRepository = serviceUnitRepository;
    }

    public StockResponseDto createStock(StockDto dto) {
        ServiceUnitEntity serviceUnit = serviceUnitRepository.findById(dto.getServiceUnitId())
                .orElseThrow(() -> new StockException("Unidade de saúde não encontrada."));

        StockEntity stock = new StockEntity();
        stock.setServiceUnit(serviceUnit);
        stock.setName(dto.getName());
        stock.setQuantity(dto.getQuantity());
        stock.setMinimumQuantity(dto.getMinimumQuantity());

        return convertToDto(stockRepository.save(stock));
    }

    public StockResponseDto updateStock(Long id, StockDto dto) {
        StockEntity stock = stockRepository.findById(id)
                .orElseThrow(() -> new StockException("Estoque não encontrado com ID: " + id));

        stock.setName(dto.getName());
        stock.setQuantity(dto.getQuantity());
        stock.setMinimumQuantity(dto.getMinimumQuantity());

        return convertToDto(stockRepository.save(stock));
    }

    public void deleteStock(Long id) {
        StockEntity stock = stockRepository.findById(id)
                .orElseThrow(() -> new StockException("Estoque não encontrado com ID: " + id));

        stockRepository.delete(stock);
    }

    public Optional<StockResponseDto> getStockById(Long id) {
        return stockRepository.findById(id).map(this::convertToDto);
    }

    public List<StockResponseDto> listStock() {
        return stockRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<StockResponseDto> listByServiceUnit(Long serviceUnitId) {
        return stockRepository.findByServiceUnitId(serviceUnitId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private StockResponseDto convertToDto(StockEntity entity) {
        return new StockResponseDto(
                entity.getId(),
                entity.getServiceUnit().getName(),
                entity.getName(),
                entity.getQuantity(),
                entity.getMinimumQuantity()
        );
    }
}