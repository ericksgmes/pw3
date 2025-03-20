package utils;

public class CpfValidator {
    public boolean validateCpf(String cpfDigits) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (cpfDigits.charAt(i) - '0') * (10 - i);
        }
        int remainder = sum % 11;
        int firstCheckDigit = (remainder < 2) ? 0 : 11 - remainder;

        if (firstCheckDigit != (cpfDigits.charAt(9) - '0')) {
            return false;
        }

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (cpfDigits.charAt(i) - '0') * (11 - i);
        }
        remainder = sum % 11;
        int secondCheckDigit = (remainder < 2) ? 0 : 11 - remainder;

        return secondCheckDigit == (cpfDigits.charAt(10) - '0');
    }
}
