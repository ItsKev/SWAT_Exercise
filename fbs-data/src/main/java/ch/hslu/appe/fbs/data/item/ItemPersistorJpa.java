package ch.hslu.appe.fbs.data.item;

import ch.hslu.appe.fbs.model.PersistenceUnit;
import ch.hslu.appe.fbs.model.db.Item;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public final class ItemPersistorJpa implements ItemPersistor {

    private final EntityManager entityManager;

    ItemPersistorJpa() {
        this.entityManager = PersistenceUnit.getEntityManager();
    }

    @Override
    public List<Item> getAllItems() {
        final TypedQuery<Item> orderQuery = this.entityManager.createQuery("SELECT o FROM Item o", Item.class);
        return orderQuery.getResultList();
    }

    @Override
    public void save(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(item);
        this.entityManager.getTransaction().commit();

        this.entityManager.getTransaction().begin();
        this.entityManager.refresh(item);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public void update(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        this.entityManager.getTransaction().begin();
        final Item persItem = this.entityManager.find(Item.class, item.getId());
        if (persItem != null) {
            persItem.setName(item.getName());
            persItem.setPrice(item.getPrice());
            persItem.setArtNr(item.getArtNr());
            persItem.setOrderItemsById(item.getOrderItemsById());
            persItem.setReordersById(item.getReordersById());
        }
        this.entityManager.getTransaction().commit();

        this.entityManager.getTransaction().begin();
        this.entityManager.refresh(persItem);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public void updateStock(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        this.entityManager.getTransaction().begin();
        final Item persItem = this.entityManager.find(Item.class, item.getId());
        if (persItem != null) {
            persItem.setLocalStock(item.getLocalStock());
            persItem.setMinLocalStock(item.getMinLocalStock());
            persItem.setVirtualLocalStock(item.getVirtualLocalStock());
        }
        this.entityManager.getTransaction().commit();

        this.entityManager.getTransaction().begin();
        this.entityManager.refresh(persItem);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public Optional<Item> getItemById(int id) {
        final TypedQuery<Item> itemQuery = this.entityManager.createQuery("SELECT i FROM Item i WHERE i.id = :id", Item.class);
        itemQuery.setParameter("id", id);
        try {
            final Item result = itemQuery.getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
