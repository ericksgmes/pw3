package model;

import jakarta.persistence.*;
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

    @Column(name = "ra", unique = true, nullable = false)
    private String ra;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "nota1")
    private double nota1;

    @Column(name = "nota2")
    private double nota2;

    @Column(name = "nota3")
    private double nota3;

    @Column(name = "aprovado")
    private boolean aprovado;

    public boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }

    public Aluno() {
    }

    public Aluno(String nome, String ra, String email) {
        this.nome = new Nome(nome);
        this.ra = ra;
        this.email = email;
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

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getNota1() {
        return nota1;
    }

    public void setNota1(double nota1) {
        this.nota1 = nota1;
    }

    public double getNota2() {
        return nota2;
    }

    public void setNota2(double nota2) {
        this.nota2 = nota2;
    }

    public double getNota3() {
        return nota3;
    }

    public void setNota3(double nota3) {
        this.nota3 = nota3;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "\n\tuuid=" + uuid +
                ",\n\tnome=" + nome +
                ",\n\tra='" + ra + '\'' +
                ",\n\temail='" + email + '\'' +
                ",\n\tnota1=" + nota1 +
                ",\n\tnota2=" + nota2 +
                ",\n\tnota3=" + nota3 +
                "\n}";
    }
}
