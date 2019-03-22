package ch.hslu.appe.fbs.common.dto;

import java.io.Serializable;
import java.util.Objects;

public final class BillDTO implements Serializable {
    private final Integer id;
    private final Integer orderId;
    private final Integer price;

    public BillDTO(Integer id, Integer orderId, Integer price) {
        this.id = id;
        this.orderId = orderId;
        this.price = price;
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getOrderId() {
        return this.orderId;
    }

    public Integer getPrice() {
        return this.price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BillDTO)) return false;
        BillDTO billDTO = (BillDTO) o;
        return Objects.equals(this.orderId, billDTO.orderId) &&
                Objects.equals(this.price, billDTO.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.orderId, this.price);
    }
}
