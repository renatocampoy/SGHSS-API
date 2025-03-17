package br.eng.campoy.sghssbackend.types;

import java.util.Objects;
import java.util.regex.Pattern;

public class Cpf {

    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{11}");
    private final String value;

    public Cpf(String value) {
        if (!isValid(value)) {
            throw new IllegalArgumentException("CPF inválido: " + value);
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isValid(String cpf) {
        if (cpf == null || !CPF_PATTERN.matcher(cpf).matches()) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (exemplo: 11111111111)
        if (cpf.chars().distinct().count() == 1) {
            return false;
        }

        // Valida dígitos verificadores
        return isValidCpfDigits(cpf);
    }

    private static boolean isValidCpfDigits(String cpf) {
        int[] weights = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int firstDigit = calculateDigit(cpf.substring(0, 9), weights);

        weights = new int[]{11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
        int secondDigit = calculateDigit(cpf.substring(0, 9) + firstDigit, weights);

        return cpf.equals(cpf.substring(0, 9) + firstDigit + secondDigit);
    }

    private static int calculateDigit(String base, int[] weights) {
        int sum = 0;
        for (int i = 0; i < base.length(); i++) {
            sum += Character.getNumericValue(base.charAt(i)) * weights[i];
        }
        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }

    @Override
    public String toString() {
        return formatCpf(value);
    }

    private String formatCpf(String value) {
        return value.substring(0, 3) + "." + value.substring(3, 6) + "." + value.substring(6, 9) + "-" + value.substring(9);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cpf cpf = (Cpf) o;
        return Objects.equals(value, cpf.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}