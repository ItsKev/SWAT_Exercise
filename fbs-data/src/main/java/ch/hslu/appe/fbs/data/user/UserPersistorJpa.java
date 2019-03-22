package ch.hslu.appe.fbs.data.user;

import ch.hslu.appe.fbs.model.PersistenceUnit;
import ch.hslu.appe.fbs.model.db.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public final class UserPersistorJpa implements UserPersistor {

    private final EntityManager entityManager;

    UserPersistorJpa() {
        this.entityManager = PersistenceUnit.getEntityManager();
    }

    @Override
    public Optional<User> getById(final int userId) {
        final TypedQuery<User> userQuery = this.entityManager.createQuery("SELECT o FROM User o WHERE o.id = :id", User.class);
        userQuery.setParameter("id", userId);
        try {
            final User result = userQuery.getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getByName(final String name) {
        final TypedQuery<User> userQuery = this.entityManager.createQuery("SELECT o FROM User o WHERE o.userName = :userName", User.class);
        userQuery.setParameter("userName", name);
        try {
            final User result = userQuery.getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> getAll() {
        final TypedQuery<User> orderQuery = this.entityManager.createQuery("SELECT o FROM User o", User.class);
        return orderQuery.getResultList();
    }

    @Override
    public void save(final User user) {
        if (user == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(user);
        this.entityManager.getTransaction().commit();

        this.entityManager.getTransaction().begin();
        this.entityManager.refresh(user);
        this.entityManager.getTransaction().commit();
    }
}
