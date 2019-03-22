package ch.hslu.appe.fbs.model;

import ch.hslu.appe.fbs.model.db.*;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;

final class DataTruncator {

    private final EntityManager entityManager;

    DataTruncator() {
        this.entityManager = PersistenceUnit.getEntityManager();
    }

    void deleteDataInEntities() {
        truncateEntity(OrderItem.class);
        truncateEntity(Reminder.class);
        truncateEntity(Reorder.class);
        truncateEntity(Bill.class);
        truncateEntity(Order.class);
        truncateEntity(OrderState.class);
        truncateEntity(Item.class);
        truncateEntity(User.class);
        truncateEntity(UserRole.class);
        truncateEntity(Customer.class);
    }

    private <T> void truncateEntity(final Class<T> entity) {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaDelete<T> query = builder.createCriteriaDelete(entity);
        query.from(entity);
        this.entityManager.getTransaction().begin();
        this.entityManager.createQuery(query).executeUpdate();
        this.entityManager.getTransaction().commit();
    }
}
