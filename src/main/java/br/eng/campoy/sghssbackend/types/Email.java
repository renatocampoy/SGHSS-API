package br.eng.campoy.sghssbackend.types;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email {

    private final String value;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    );

    public Email(String value) {
        if (!isValid(value)) {
            throw new IllegalArgumentException("Email inválido: " + value);
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isValid(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}