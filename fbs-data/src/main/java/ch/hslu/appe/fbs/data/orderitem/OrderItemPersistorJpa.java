package ch.hslu.appe.fbs.data.orderitem;

import ch.hslu.appe.fbs.model.PersistenceUnit;
import ch.hslu.appe.fbs.model.db.OrderItem;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public final class OrderItemPersistorJpa implements OrderItemPersistor {

    private final EntityManager entityManager;

    OrderItemPersistorJpa() {
        this.entityManager = PersistenceUnit.getEntityManager();
    }

    @Override
    public Optional<OrderItem> getByOrderIdItemId(int orderId, int itemId) {
        final TypedQuery<OrderItem> orderItemQuery = this.entityManager.createQuery("SELECT oi FROM OrderItem oi WHERE oi.orderId = :orderId " +
                "AND oi.itemId = :itemId", OrderItem.class);
        orderItemQuery.setParameter("orderId", orderId);
        orderItemQuery.setParameter("itemId", itemId);
        try {
            final OrderItem result = orderItemQuery.getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<OrderItem> getByOrderId(int orderId) {
        final TypedQuery<OrderItem> orderItemQuery = this.entityManager.createQuery("SELECT oi FROM OrderItem oi WHERE oi.orderId = :orderId",
                OrderItem.class);
        orderItemQuery.setParameter("orderId", orderId);
        return orderItemQuery.getResultList();
    }

    @Override
    public List<OrderItem> getByItemId(int itemId) {
        final TypedQuery<OrderItem> orderItemQuery = this.entityManager.createQuery("SELECT oi FROM OrderItem oi WHERE oi.itemId = :itemId",
                OrderItem.class);
        orderItemQuery.setParameter("itemId", itemId);
        return orderItemQuery.getResultList();
    }

    @Override
    public List<OrderItem> getAll() {
        final TypedQuery<OrderItem> orderItemQuery = this.entityManager.createQuery("SELECT oi FROM OrderItem oi", OrderItem.class);
        return orderItemQuery.getResultList();
    }

    @Override
    public void save(OrderItem orderItem) {
        if (orderItem == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(orderItem);
        this.entityManager.getTransaction().commit();

        this.entityManager.getTransaction().begin();
        this.entityManager.refresh(orderItem);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public void saveAll(List<OrderItem> orderItems) {
        if (orderItems == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        if (!orderItems.isEmpty()) {
            this.entityManager.getTransaction().begin();
            orderItems.forEach(this.entityManager::persist);
            this.entityManager.getTransaction().commit();

            this.entityManager.getTransaction().begin();
            orderItems.forEach(this.entityManager::refresh);
            this.entityManager.getTransaction().commit();
        }
    }
}
