package ch.hslu.appe.fbs.data.customer;

import ch.hslu.appe.fbs.model.PersistenceUnit;
import ch.hslu.appe.fbs.model.db.Customer;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public final class CustomerPersistorJpa implements CustomerPersistor {

    private final EntityManager entityManager;

    CustomerPersistorJpa() {
        this.entityManager = PersistenceUnit.getEntityManager();
    }

    @Override
    public Optional<Customer> getById(final int customerId) {
        final TypedQuery<Customer> customerQuery = this.entityManager.createQuery("SELECT c FROM Customer c WHERE c.id = :id", Customer.class);
        customerQuery.setParameter("id", customerId);
        try {
            final Customer result = customerQuery.getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> getAll() {
        final TypedQuery<Customer> customerQuery = this.entityManager.createQuery("SELECT c FROM Customer c", Customer.class);
        return customerQuery.getResultList();
    }

    @Override
    public void save(final Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(customer);
        this.entityManager.getTransaction().commit();

        this.entityManager.getTransaction().begin();
        this.entityManager.refresh(customer);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public void update(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        this.entityManager.getTransaction().begin();
        final Customer persCustomer = this.entityManager.find(Customer.class, customer.getId());
        if (persCustomer != null) {
            persCustomer.setPrename(customer.getPrename());
            persCustomer.setSurname(customer.getSurname());
            persCustomer.setAdress(customer.getAdress());
            persCustomer.setPlz(customer.getPlz());
            persCustomer.setCity(customer.getCity());
            persCustomer.setOrdersById(customer.getOrdersById());
        }
        this.entityManager.getTransaction().commit();

        this.entityManager.getTransaction().begin();
        this.entityManager.refresh(persCustomer);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public void delete(final Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        final TypedQuery<Customer> customerQuery = this.entityManager.createQuery("DELETE FROM Customer c WHERE c.id = :id", Customer.class);
        customerQuery.setParameter("id", customer.getId());
        this.entityManager.getTransaction().begin();
        customerQuery.executeUpdate();
        this.entityManager.getTransaction().commit();
    }
}
