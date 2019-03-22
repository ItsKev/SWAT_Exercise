package ch.hslu.appe.fbs.data.userrole;

import ch.hslu.appe.fbs.model.PersistenceUnit;
import ch.hslu.appe.fbs.model.db.UserRole;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public final class UserRolePersistorJpa implements UserRolePersistor {

    private final EntityManager entityManager;

    UserRolePersistorJpa() {
        this.entityManager = PersistenceUnit.getEntityManager();
    }

    @Override
    public Optional<UserRole> getById(int roleId) {
        final TypedQuery<UserRole> userRoleQuery = this.entityManager.createQuery("SELECT ur FROM UserRole ur WHERE ur.id = :id",
                UserRole.class);
        userRoleQuery.setParameter("id", roleId);
        try {
            final UserRole result = userRoleQuery.getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserRole> getByName(String name) {
        final TypedQuery<UserRole> userRoleQuery = this.entityManager.createQuery("SELECT ur FROM UserRole ur WHERE ur.roleName = :name",
                UserRole.class);
        userRoleQuery.setParameter("name", name);
        try {
            final UserRole result = userRoleQuery.getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<UserRole> getAll() {
        final TypedQuery<UserRole> userRoleQuery = this.entityManager.createQuery("SELECT ur FROM UserRole ur", UserRole.class);
        return userRoleQuery.getResultList();
    }
}
