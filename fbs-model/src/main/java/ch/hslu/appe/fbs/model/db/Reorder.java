package ch.hslu.appe.fbs.model.db;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "reorder", schema = "grp05")
public class Reorder {
    private Integer id;
    private Integer itemId;
    private Integer quantity;
    private Timestamp reorderDate;
    private Timestamp delivered;
    private Item itemByItemId;

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
    @Column(name = "itemId")
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "reorderDate")
    public Timestamp getReorderDate() {
        return reorderDate;
    }

    public void setReorderDate(Timestamp reorderDate) {
        this.reorderDate = reorderDate;
    }

    @Basic
    @Column(name = "delivered")
    public Timestamp getDelivered() {
        return delivered;
    }

    public void setDelivered(Timestamp delivered) {
        this.delivered = delivered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reorder reorder = (Reorder) o;
        return Objects.equals(id, reorder.id) &&
                Objects.equals(itemId, reorder.itemId) &&
                Objects.equals(quantity, reorder.quantity) &&
                Objects.equals(reorderDate, reorder.reorderDate) &&
                Objects.equals(delivered, reorder.delivered);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, itemId, quantity, reorderDate, delivered);
    }

    @ManyToOne
    @JoinColumn(name = "itemId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Item getItemByItemId() {
        return itemByItemId;
    }

    public void setItemByItemId(Item itemByItemId) {
        this.itemByItemId = itemByItemId;
    }
}
