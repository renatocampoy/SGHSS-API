package br.eng.campoy.sghssbackend.domain.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StockRepository extends JpaRepository<StockEntity, Long> {
    List<StockEntity> findByServiceUnitId(Long serviceUnitId);
}