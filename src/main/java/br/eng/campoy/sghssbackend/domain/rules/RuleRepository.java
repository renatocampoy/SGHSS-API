package br.eng.campoy.sghssbackend.domain.rules;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RuleRepository extends JpaRepository<RuleEntity, Long> {
    Optional<RuleEntity> findByName(String name);
}