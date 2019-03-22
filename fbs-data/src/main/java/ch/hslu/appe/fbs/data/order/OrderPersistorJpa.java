package ch.hslu.appe.fbs.data.order;

import ch.hslu.appe.fbs.model.PersistenceUnit;
import ch.hslu.appe.fbs.model.db.Order;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public final class OrderPersistorJpa implements OrderPersistor {

    private final EntityManager entityManager;

    OrderPersistorJpa() {
        this.entityManager = PersistenceUnit.getEntityManager();
    }

    @Override
    public Optional<Order> getById(final int orderId) {
        final TypedQuery<Order> orderQuery = this.entityManager.createQuery("SELECT o FROM Order o WHERE o.id = :id", Order.class);
        orderQuery.setParameter("id", orderId);
        try {
            final Order result = orderQuery.getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Order> getAll() {
        final TypedQuery<Order> orderQuery = this.entityManager.createQuery("SELECT o FROM Order o", Order.class);
        return orderQuery.getResultList();
    }

    @Override
    public List<Order> getByCustomer(final int customerId) {
        final TypedQuery<Order> orderQuery = this.entityManager.createQuery("SELECT o FROM Order o WHERE o.customerId = :customerId", Order.class);
        orderQuery.setParameter("customerId", customerId);
        final List<Order> orders = orderQuery.getResultList();
        return orderQuery.getResultList();
    }

    @Override
    public List<Order> getByCustomerAndOrderState(int customerId, int stateId) {
        final TypedQuery<Order> orderQuery = this.entityManager.createQuery("SELECT o FROM Order o WHERE o.customerId = :customerId " +
                "AND o.orderState = :state", Order.class);
        orderQuery.setParameter("customerId", customerId);
        orderQuery.setParameter("state", stateId);
        return orderQuery.getResultList();
    }

    @Override
    public void save(final Order order) {
        if (order == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(order);
        this.entityManager.getTransaction().commit();

        this.entityManager.getTransaction().begin();
        this.entityManager.refresh(order);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public void update(final Order order) {
        if (order == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        this.entityManager.getTransaction().begin();
        final Order persOrder = this.entityManager.find(Order.class, order.getId());
        if (persOrder != null) {
            persOrder.setUserId(order.getUserId());
            persOrder.setCustomerId(order.getCustomerId());
            persOrder.setOrderState(order.getOrderState());
            persOrder.setDateTime(order.getDateTime());
            persOrder.setOrderItemsById(order.getOrderItemsById());
            persOrder.setBillsById(order.getBillsById());
        }
        this.entityManager.getTransaction().commit();

        this.entityManager.getTransaction().begin();
        this.entityManager.refresh(persOrder);
        this.entityManager.getTransaction().commit();
    }
}
