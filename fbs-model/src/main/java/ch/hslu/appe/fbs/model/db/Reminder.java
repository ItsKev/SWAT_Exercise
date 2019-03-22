package ch.hslu.appe.fbs.model.db;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "reminder", schema = "grp05")
public class Reminder {
    private Integer id;
    private Integer billId;
    private Bill billByBillId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "billId")
    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reminder reminder = (Reminder) o;
        return Objects.equals(id, reminder.id) &&
                Objects.equals(billId, reminder.billId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, billId);
    }

    @ManyToOne
    @JoinColumn(name = "billId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Bill getBillByBillId() {
        return billByBillId;
    }

    public void setBillByBillId(Bill billByBillId) {
        this.billByBillId = billByBillId;
    }
}
