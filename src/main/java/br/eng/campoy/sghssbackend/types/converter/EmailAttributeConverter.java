package br.eng.campoy.sghssbackend.types.converter;

import br.eng.campoy.sghssbackend.types.Email;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EmailAttributeConverter implements AttributeConverter<Email, String> {

    @Override
    public String convertToDatabaseColumn(Email email) {
        return (email != null) ? email.getValue() : null;
    }

    @Override
    public Email convertToEntityAttribute(String dbData) {
        return (dbData != null) ? new Email(dbData) : null;
    }
}