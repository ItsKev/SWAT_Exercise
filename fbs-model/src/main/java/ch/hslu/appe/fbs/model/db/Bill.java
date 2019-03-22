package ch.hslu.appe.fbs.model.db;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "bill", schema = "grp05")
public class Bill {
    private Integer id;
    private Integer orderId;
    private Integer price;
    private Order orderByOrderId;
    private Collection<Reminder> remindersById;

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
    @Column(name = "orderId")
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "price")
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return Objects.equals(id, bill.id) &&
                Objects.equals(orderId, bill.orderId) &&
                Objects.equals(price, bill.price);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, orderId, price);
    }

    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Order getOrderByOrderId() {
        return orderByOrderId;
    }

    public void setOrderByOrderId(Order orderByOrderId) {
        this.orderByOrderId = orderByOrderId;
    }

    @OneToMany(mappedBy = "billByBillId", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    public Collection<Reminder> getRemindersById() {
        return remindersById;
    }

    public void setRemindersById(Collection<Reminder> remindersById) {
        this.remindersById = remindersById;
    }
}
