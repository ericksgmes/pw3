package repository;

import jakarta.persistence.EntityManager;
import model.Aluno;
import utils.PersistService;
import java.util.List;
import java.util.UUID;

public class AlunoDao {

    private final EntityManager em;

    public AlunoDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Aluno aluno) {
        PersistService.persist(this.em ,aluno);
    }

    public Aluno buscarPorId(UUID id) {
        try {
            return em.find(Aluno.class, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Aluno> listarAlunos() {
        try {
            return em.createQuery("SELECT a FROM Aluno a", Aluno.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Aluno> listarAlunosAprovados() {
        try {
            return em.createQuery("SELECT a FROM Aluno a WHERE a.aprovado = true", Aluno.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void atualizar(Aluno aluno) {
        PersistService.atualizar(this.em ,aluno);
    }

    public void remover(Aluno aluno) {
        PersistService.remover(this.em, aluno);
    }

    public List<Aluno> buscarPorNome(String nome) {
        try {
            return em.createQuery("SELECT a FROM Aluno a WHERE a.nome.nome LIKE :nome", Aluno.class)
                    .setParameter("nome", "%" + nome + "%")
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar aluno por nome", e);
        }
    }

}
