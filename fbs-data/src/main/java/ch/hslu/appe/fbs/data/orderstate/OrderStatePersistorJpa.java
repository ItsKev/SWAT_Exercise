package ch.hslu.appe.fbs.data.orderstate;

import ch.hslu.appe.fbs.model.PersistenceUnit;
import ch.hslu.appe.fbs.model.db.OrderState;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public final class OrderStatePersistorJpa implements OrderStatePersistor {

    private final EntityManager entityManager;

    OrderStatePersistorJpa() {
        this.entityManager = PersistenceUnit.getEntityManager();
    }

    @Override
    public Optional<OrderState> getByState(String state) {
        final TypedQuery<OrderState> orderStateQuery = this.entityManager.createQuery("SELECT os FROM OrderState os WHERE os.state = :osState",
                OrderState.class);
        orderStateQuery.setParameter("osState", state);
        try {
            final OrderState result = orderStateQuery.getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<OrderState> getById(int orderStateId) {
        final TypedQuery<OrderState> orderStateQuery = this.entityManager.createQuery("SELECT os FROM OrderState os WHERE os.id = :osId",
                OrderState.class);
        orderStateQuery.setParameter("osId", orderStateId);
        try {
            final OrderState result = orderStateQuery.getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<OrderState> getAll() {
        final TypedQuery<OrderState> orderStateQuery = this.entityManager.createQuery("SELECT os FROM OrderState os", OrderState.class);
        return orderStateQuery.getResultList();
    }
}
