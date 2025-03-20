package utils;

import jakarta.persistence.EntityManager;
import model.Aluno;

import java.util.List;

public class PersistService {
    public static void persist(EntityManager em, Object param)  {
        try {
            if (param == null) {
                throw new IllegalArgumentException();
            }
            em.getTransaction().begin();
            em.persist(param);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }

    public static void remover(EntityManager em, Object param) {
        try {
            em.getTransaction().begin();
            em.remove(param);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }

    public static void atualizar(EntityManager em, Object param) {
        try {
            em.getTransaction().begin();
            em.merge(param);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }
}
