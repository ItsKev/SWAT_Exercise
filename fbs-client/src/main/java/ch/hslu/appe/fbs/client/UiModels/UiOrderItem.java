package ch.hslu.appe.fbs.client.UiModels;

import ch.hslu.appe.fbs.common.dto.OrderItemDTO;

public class UiOrderItem {
    private Integer price;
    private Integer quantity;
    private UiItem uiItemId;

    public UiOrderItem(Integer price, UiItem uiItem) {
        this.price = price;
        this.quantity = 1;
        this.uiItemId = uiItem;
    }

    public UiOrderItem(OrderItemDTO orderItemDTO) {
        this.price = orderItemDTO.getPrice();
        this.quantity = orderItemDTO.getQuantity();
        this.uiItemId = new UiItem(orderItemDTO.getItemByItemId());
    }

    public Integer getId() {
        return uiItemId.getId();
    }

    public String getArtNr() {
        return uiItemId.getArtNr();
    }

    public String getName() {
        return uiItemId.getName();
    }

    public Integer getPrice() {
        return this.price;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public UiItem getUiItemId() {
        return this.uiItemId;
    }

    public void incrementQuantity() {
        this.quantity++;
    }

    public OrderItemDTO getOrderDTO() {
        return new OrderItemDTO(this.price, this.quantity, this.uiItemId.getItemDTO());
    }
}
