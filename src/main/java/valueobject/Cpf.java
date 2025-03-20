package valueobject;

import jakarta.persistence.Embeddable;
import utils.CpfValidator;

@Embeddable
public class Cpf {
    protected Cpf() {}
    private String CPF;

    public Cpf(String CPF) {
        if (CPF == null) {
            throw new IllegalArgumentException("CPF não pode ser nulo");
        }

        String cleanedCpf = CPF.replaceAll("\\D", "");

        if (cleanedCpf.length() != 11) {
            throw new IllegalArgumentException("CPF deve ter 11 dígitos");
        }

        if (cleanedCpf.chars().distinct().count() == 1) {
            throw new IllegalArgumentException("Todos os dígitos não podem ser iguais");
        }

        CpfValidator validator = new CpfValidator();
        if (!validator.validateCpf(cleanedCpf)) {
            throw new IllegalArgumentException("CPF inválido");
        }

        this.CPF = cleanedCpf;
    }

    public String getValue() {
        return this.CPF;
    }

    @Override
    public String toString() {
        return this.CPF;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cpf other)) return false;
        return CPF.equals(other.CPF);
    }

    @Override
    public int hashCode() {
        return CPF.hashCode();
    }
}
