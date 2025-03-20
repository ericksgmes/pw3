package model;

import jakarta.persistence.*;
import valueobject.Cpf;
import valueobject.DataNascimento;
import valueobject.Nome;

import java.util.UUID;

@Entity
@Table(name = "alunos")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Embedded
    private Nome nome;

    @Embedded
    @AttributeOverride(name = "CPF", column = @Column(name = "cpf", unique = true))
    private Cpf cpf;

    @Embedded
    private DataNascimento dataNascimento;

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    private boolean aprovado;

    public Aluno() {}

    public Aluno(String nome, String cpf) {
        this.nome = new Nome(nome);
        this.cpf = new Cpf(cpf);
    }

    public UUID getUuid() {
        return uuid;
    }

    public Nome getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = new Nome(nome);
    }

    public Cpf getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = new Cpf(cpf);
    }

    public DataNascimento getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = new DataNascimento(dataNascimento);
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "\n\tuuid=" + uuid.toString() +
                ",\n\tnome=" + nome.toString() +
                ",\n\tcpf=" + cpf.toString() +
                ",\n\tdataNascimento=" + dataNascimento.toString() +
                ",\n\tdisciplina=" + disciplina.toString() +
                "\n}";
    }
}
