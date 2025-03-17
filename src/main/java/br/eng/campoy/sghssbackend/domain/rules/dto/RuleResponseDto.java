package br.eng.campoy.sghssbackend.domain.rules.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RuleResponseDto {
    private Long id;
    private String name;
}