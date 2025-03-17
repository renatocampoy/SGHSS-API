package br.eng.campoy.sghssbackend.types;

import java.util.Objects;

public class Age {

    private final String value;

    public Age(String value) {
//        int int_value = Integer.parseInt(value);
//        if (!isValid(int_value)) {
//            throw new IllegalArgumentException("Idade inválida: " + value);
//        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isValid(int age) {
        // Define limites para idade: 0 (bebê) a 150 (idade máxima plausível)
        return age >= 0 && age <= 150;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Age age = (Age) o;
        return value == age.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}