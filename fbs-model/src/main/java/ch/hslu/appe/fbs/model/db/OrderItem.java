package ch.hslu.appe.fbs.model.db;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "order_item", schema = "grp05")
@IdClass(OrderItemPK.class)
public class OrderItem {
    private Integer orderId;
    private Integer itemId;
    private Integer price;
    private Integer quantity;
    private Order orderByOrderId;
    private Item itemByItemId;

    @Id
    @Column(name = "orderId")
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Id
    @Column(name = "itemId")
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "price")
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Basic
    @Column(name = "quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(orderId, orderItem.orderId) &&
                Objects.equals(itemId, orderItem.itemId) &&
                Objects.equals(price, orderItem.price) &&
                Objects.equals(quantity, orderItem.quantity);
    }

    @Override
    public int hashCode() {

        return Objects.hash(orderId, itemId, price, quantity);
    }

    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Order getOrderByOrderId() {
        return orderByOrderId;
    }

    public void setOrderByOrderId(Order orderByOrderId) {
        this.orderByOrderId = orderByOrderId;
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
