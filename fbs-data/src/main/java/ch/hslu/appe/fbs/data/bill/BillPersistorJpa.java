package ch.hslu.appe.fbs.data.bill;

import ch.hslu.appe.fbs.model.PersistenceUnit;
import ch.hslu.appe.fbs.model.db.Bill;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public final class BillPersistorJpa implements BillPersistor {

    private final EntityManager entityManager;

    BillPersistorJpa() {
        this.entityManager = PersistenceUnit.getEntityManager();
    }

    @Override
    public Optional<Bill> getById(int billId) {
        final TypedQuery<Bill> billQuery = this.entityManager.createQuery("SELECT b FROM Bill b WHERE b.id = :id", Bill.class);
        billQuery.setParameter("id", billId);
        try {
            final Bill result = billQuery.getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Bill> getByOrderId(int orderId) {
        final TypedQuery<Bill> billQuery = this.entityManager.createQuery("SELECT b FROM Bill b WHERE b.orderId = :orderId", Bill.class);
        billQuery.setParameter("orderId", orderId);
        return billQuery.getResultList();
    }

    @Override
    public List<Bill> getAll() {
        final TypedQuery<Bill> billQuery = this.entityManager.createQuery("SELECT b FROM Bill b", Bill.class);
        return billQuery.getResultList();
    }

    @Override
    public void save(Bill bill) {
        if (bill == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(bill);
        this.entityManager.getTransaction().commit();

        this.entityManager.getTransaction().begin();
        this.entityManager.refresh(bill);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public void update(Bill bill) {
        if (bill == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        this.entityManager.getTransaction().begin();
        final Bill persBill = this.entityManager.find(Bill.class, bill.getId());
        if (persBill != null) {
            persBill.setOrderId(bill.getOrderId());
            persBill.setPrice(bill.getPrice());
            persBill.setRemindersById(bill.getRemindersById());
        }
        this.entityManager.getTransaction().commit();

        this.entityManager.getTransaction().begin();
        this.entityManager.refresh(persBill);
        this.entityManager.getTransaction().commit();
    }
}
