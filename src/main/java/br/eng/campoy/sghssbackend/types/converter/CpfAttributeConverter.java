package br.eng.campoy.sghssbackend.types.converter;

import br.eng.campoy.sghssbackend.types.Cpf;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CpfAttributeConverter implements AttributeConverter<Cpf, String> {

    @Override
    public String convertToDatabaseColumn(Cpf cpf) {
        return (cpf != null) ? cpf.getValue() : null;
    }

    @Override
    public Cpf convertToEntityAttribute(String dbData) {
        return (dbData != null) ? new Cpf(dbData) : null;
    }
}
