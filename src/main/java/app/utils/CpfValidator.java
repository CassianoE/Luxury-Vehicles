package app.utils;

import org.springframework.stereotype.Component;

@Component
public class CpfValidator {

    public static boolean isValidCPF(String cpf) {
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se tem exatamente 11 dígitos
        if (cpf.length() != 11) return false;

        // Verifica se todos os dígitos são iguais (ex: "11111111111" é inválido)
        if (cpf.matches("(\\d)\\1{10}")) return false;

        // Valida os dígitos verificadores
        return isValidCPFCheckDigits(cpf);
    }

    private static boolean isValidCPFCheckDigits(String cpf) {
        int sum1 = 0, sum2 = 0;
        for (int i = 0; i < 9; i++) {
            int num = cpf.charAt(i) - '0';
            sum1 += num * (10 - i);
            sum2 += num * (11 - i);
        }

        // Primeiro dígito verificador
        int digit1 = (sum1 * 10) % 11;
        if (digit1 == 10) digit1 = 0;
        if (digit1 != cpf.charAt(9) - '0') return false;

        // Segundo dígito verificador
        sum2 += digit1 * 2;
        int digit2 = (sum2 * 10) % 11;
        if (digit2 == 10) digit2 = 0;
        return digit2 == cpf.charAt(10) - '0';
    }
}