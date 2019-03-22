package ch.hslu.appe.fbs.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class PersistenceUnit {

    private static final String PERSISTENCE_UNIT = "FBS_PU";

    private static EntityManager entityManager = null;

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            entityManager = emf.createEntityManager();
        }
        return entityManager;
    }
}
