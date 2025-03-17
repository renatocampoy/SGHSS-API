package br.eng.campoy.sghssbackend.domain.rules;

import br.eng.campoy.sghssbackend.domain.rules.dto.RuleDto;
import br.eng.campoy.sghssbackend.domain.rules.dto.RuleResponseDto;
import br.eng.campoy.sghssbackend.domain.rules.exception.RuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RuleService {

    private final RuleRepository ruleRepository;

    @Autowired
    public RuleService(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public RuleResponseDto createRule(RuleDto dto) {
        if (ruleRepository.findByName(dto.getName()).isPresent()) {
            throw new RuleException("Perfil já existe.");
        }

        RuleEntity rule = new RuleEntity();
        rule.setName(dto.getName());

        return convertToDto(ruleRepository.save(rule));
    }

    public List<RuleResponseDto> listAllRules() {
        return ruleRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<RuleResponseDto> getRuleById(Long id) {
        return ruleRepository.findById(id).map(this::convertToDto);
    }

    public void deleteRule(Long id) {
        RuleEntity rule = ruleRepository.findById(id)
                .orElseThrow(() -> new RuleException("Perfil não encontrado."));

        ruleRepository.delete(rule);
    }

    private RuleResponseDto convertToDto(RuleEntity entity) {
        return new RuleResponseDto(
                entity.getId(),
                entity.getName()
        );
    }
}