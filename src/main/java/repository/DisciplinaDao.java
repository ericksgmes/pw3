package repository;

import jakarta.persistence.EntityManager;
import model.Disciplina;
import utils.PersistService;

public class DisciplinaDao {

    private final EntityManager em;

    public DisciplinaDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Disciplina disciplina) {
        PersistService.persist(this.em, disciplina);
    }

    public Disciplina buscarPorId(Long id) {
        try {
            return em.find(Disciplina.class, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void atualizar(Disciplina disciplina) {
        PersistService.atualizar(this.em, disciplina);
    }

    public void remover(Disciplina disciplina) {
        PersistService.remover(this.em, disciplina);
    }
}
