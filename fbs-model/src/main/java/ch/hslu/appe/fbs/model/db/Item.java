package ch.hslu.appe.fbs.model.db;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "item", schema = "grp05")
public class Item {
    private Integer id;
    private String name;
    private Integer price;
    private String artNr;
    private Integer localStock;
    private Integer minLocalStock;
    private Integer virtualLocalStock;
    private Collection<OrderItem> orderItemsById;
    private Collection<Reorder> reordersById;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "ArtNr")
    public String getArtNr() {
        return artNr;
    }

    public void setArtNr(String artNr) {
        this.artNr = artNr;
    }

    @Basic
    @Column(name = "localStock")
    public Integer getLocalStock() {
        return localStock;
    }

    public void setLocalStock(Integer localStock) {
        this.localStock = localStock;
    }

    @Basic
    @Column(name = "minLocalStock")
    public Integer getMinLocalStock() {
        return minLocalStock;
    }

    public void setMinLocalStock(Integer minLocalStock) {
        this.minLocalStock = minLocalStock;
    }

    @Basic
    @Column(name = "virtualLocalStock")
    public Integer getVirtualLocalStock() {
        return virtualLocalStock;
    }

    public void setVirtualLocalStock(Integer virtualLocalStock) {
        this.virtualLocalStock = virtualLocalStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id) &&
                Objects.equals(name, item.name) &&
                Objects.equals(price, item.price) &&
                Objects.equals(artNr, item.artNr) &&
                Objects.equals(localStock, item.localStock) &&
                Objects.equals(minLocalStock, item.minLocalStock) &&
                Objects.equals(virtualLocalStock, item.virtualLocalStock);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, price, artNr, localStock, minLocalStock, virtualLocalStock);
    }

    @OneToMany(mappedBy = "itemByItemId", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    public Collection<OrderItem> getOrderItemsById() {
        return orderItemsById;
    }

    public void setOrderItemsById(Collection<OrderItem> orderItemsById) {
        this.orderItemsById = orderItemsById;
    }

    @OneToMany(mappedBy = "itemByItemId", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    public Collection<Reorder> getReordersById() {
        return reordersById;
    }

    public void setReordersById(Collection<Reorder> reordersById) {
        this.reordersById = reordersById;
    }
}
