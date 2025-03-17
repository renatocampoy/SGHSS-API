package br.eng.campoy.sghssbackend.types.converter;

import br.eng.campoy.sghssbackend.types.Age;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AgeAttributeConverter implements AttributeConverter<Age, String> {

    @Override
    public String convertToDatabaseColumn(Age age) {
        return (age != null) ? age.getValue() : null;
    }

    @Override
    public Age convertToEntityAttribute(String dbData) {
        return (dbData != null) ? new Age(dbData) : null;
    }
}