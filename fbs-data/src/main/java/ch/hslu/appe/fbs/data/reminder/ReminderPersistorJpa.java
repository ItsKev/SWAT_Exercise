package ch.hslu.appe.fbs.data.reminder;

import ch.hslu.appe.fbs.model.PersistenceUnit;
import ch.hslu.appe.fbs.model.db.Reminder;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public final class ReminderPersistorJpa implements ReminderPersistor {

    private final EntityManager entityManager;

    ReminderPersistorJpa() {
        this.entityManager = PersistenceUnit.getEntityManager();
    }

    @Override
    public Optional<Reminder> getById(int reminderId) {
        final TypedQuery<Reminder> reminderQuery = this.entityManager.createQuery("SELECT r FROM Reminder r WHERE r.id = :id", Reminder.class);
        reminderQuery.setParameter("id", reminderId);
        try {
            final Reminder result = reminderQuery.getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Reminder> getByBillId(int billId) {
        final TypedQuery<Reminder> reminderQuery = this.entityManager.createQuery("SELECT r FROM Reminder r WHERE r.billId = :billId",
                Reminder.class);
        reminderQuery.setParameter("billId", billId);
        return reminderQuery.getResultList();
    }

    @Override
    public List<Reminder> getAll() {
        final TypedQuery<Reminder> reminderQuery = this.entityManager.createQuery("SELECT r FROM Reminder r", Reminder.class);
        return reminderQuery.getResultList();
    }

    @Override
    public void save(Reminder reminder) {
        if (reminder == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(reminder);
        this.entityManager.getTransaction().commit();

        this.entityManager.getTransaction().begin();
        this.entityManager.refresh(reminder);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public void update(Reminder reminder) {
        if (reminder == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        this.entityManager.getTransaction().begin();
        final Reminder persReminder = this.entityManager.find(Reminder.class, reminder.getId());
        if (persReminder != null) {
            persReminder.setBillId(reminder.getBillId());
        }
        this.entityManager.getTransaction().commit();

        this.entityManager.getTransaction().begin();
        this.entityManager.refresh(persReminder);
        this.entityManager.getTransaction().commit();
    }
}
