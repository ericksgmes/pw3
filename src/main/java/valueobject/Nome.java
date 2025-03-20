package valueobject;

import jakarta.persistence.Embeddable;

@Embeddable
public class Nome {
    protected Nome(){}
    private String nome;
    public Nome (String nome) {
        if (nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        if (!nome.matches("^[\\p{L}\\s]+$")) {
            throw new IllegalArgumentException("O nome deve conter somente letras e espaços.");
        }

        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    @Override
    public String toString() {
        return this.nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Nome other)) return false;
        return nome.equals(other.nome);
    }

    @Override
    public int hashCode() {
        return nome.hashCode();
    }
}
