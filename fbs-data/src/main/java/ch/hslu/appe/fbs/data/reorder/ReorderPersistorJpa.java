package ch.hslu.appe.fbs.data.reorder;

import ch.hslu.appe.fbs.model.PersistenceUnit;
import ch.hslu.appe.fbs.model.db.Reorder;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class ReorderPersistorJpa implements ReorderPersistor {

    private final EntityManager entityManager;

    ReorderPersistorJpa() {
        this.entityManager = PersistenceUnit.getEntityManager();
    }

    @Override
    public Optional<Reorder> getById(int reorderId) {
        final TypedQuery<Reorder> reorderQuery = this.entityManager.createQuery("SELECT r FROM Reorder r WHERE r.id = :id", Reorder.class);
        reorderQuery.setParameter("id", reorderId);
        try {
            final Reorder result = reorderQuery.getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Reorder> getByItemId(int itemId) {
        final TypedQuery<Reorder> reorderQuery = this.entityManager.createQuery("SELECT r FROM Reorder r WHERE r.itemId = :itemId",
                Reorder.class);
        reorderQuery.setParameter("itemId", itemId);
        return reorderQuery.getResultList();
    }

    @Override
    public List<Reorder> getByOrderId(int orderId) {
        // TODO implement after database changes
        return new ArrayList<>();
    }

    @Override
    public List<Reorder> getAll() {
        final TypedQuery<Reorder> reorderQuery = this.entityManager.createQuery("SELECT r FROM Reorder r", Reorder.class);
        return reorderQuery.getResultList();
    }

    @Override
    public List<Reorder> getActiveReorders(int itemId, int orderId) {
        // TODO modify after database changes
        final TypedQuery<Reorder> reorderQuery = this.entityManager.createQuery("SELECT r FROM Reorder r WHERE r.itemId = :itemId " +
                "AND r.delivered IS NULL", Reorder.class);
        reorderQuery.setParameter("itemId", itemId);
        return reorderQuery.getResultList();
    }

    @Override
    public void save(Reorder reorder) {
        if (reorder == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(reorder);
        this.entityManager.getTransaction().commit();

        this.entityManager.getTransaction().begin();
        this.entityManager.refresh(reorder);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public void update(Reorder reorder) {
        if (reorder == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        this.entityManager.getTransaction().begin();
        final Reorder persReorder = this.entityManager.find(Reorder.class, reorder.getId());
        if (persReorder != null) {
            persReorder.setItemId(reorder.getItemId());
            persReorder.setQuantity(reorder.getQuantity());
            persReorder.setReorderDate(reorder.getReorderDate());
            persReorder.setDelivered(reorder.getDelivered());
        }
        this.entityManager.getTransaction().commit();

        this.entityManager.getTransaction().begin();
        this.entityManager.refresh(persReorder);
        this.entityManager.getTransaction().commit();
    }
}
