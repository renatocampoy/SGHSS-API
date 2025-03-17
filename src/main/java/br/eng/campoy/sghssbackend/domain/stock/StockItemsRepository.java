package br.eng.campoy.sghssbackend.domain.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StockItemsRepository extends JpaRepository<StockItemsEntity, Long> {
    List<StockItemsEntity> findByStockId(Long stockId);
}