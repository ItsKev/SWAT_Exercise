package ch.hslu.appe.fbs.client.UiModels;

import ch.hslu.appe.fbs.client.Userinterface.Shared.UserSingleton;
import ch.hslu.appe.fbs.common.dto.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UiOrder {
    private int id;
    private CustomerDTO customer;
    private UserDTO user;
    private Timestamp dateTime;
    private List<UiOrderItem> orderItems;
    private OrderStateDTO orderState;


    public UiOrder() {
        this.orderItems = new ArrayList<>();
        this.id = -1;
    }

    public UiOrder(OrderDTO order){
        this.id = order.getId();
        this.customer = order.getCustomer();
        this.user = order.getUser();
        this.dateTime = order.getDateTime();
        this.orderState = order.getOrderState();
        this.orderItems = new ArrayList<>();

        for (OrderItemDTO orderItemDTO : order.getOrderItems()) {
            this.orderItems.add(new UiOrderItem(orderItemDTO));
        }
    }

    public Timestamp getDateTime() {
        return this.dateTime;
    }

    public int getId(){ return this.id; }

    public CustomerDTO getCustomer() {
        return this.customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public List<UiOrderItem> getOrderItems() {
        return this.orderItems;
    }

    public String getCustomerName() {
        return this.customer.toString();
    }

    public String getUserName() {
        if (this.user != null)
            return this.user.getUserName();
        else
            return null;
    }

    public String getOrderStateName() {
        if (this.orderState != null)
            return this.orderState.getOrderState();
        else
            return null;
    }

    public boolean isEmpty() {
        return this.orderItems.isEmpty() && this.customer == null;
    }

    public void addUiItem(UiItem item) {
        UiOrderItem itemToOrder = null;

        for (UiOrderItem existingItem : orderItems) {
            int i = existingItem.getUiItemId().getId();
            int y = item.getId();
            if (existingItem.getUiItemId().getId().equals(item.getId())) {
                itemToOrder = existingItem;
                break;
            }
        }

        if (itemToOrder != null) {
            itemToOrder.incrementQuantity();
        } else {
            this.orderItems.add(new UiOrderItem(item.getPrice(), item));
        }
    }

    public OrderDTO getOrderDTO() {
        this.dateTime = new Timestamp(System.currentTimeMillis());

        List<OrderItemDTO> orderItems = this.generateOrderItemDTOs();

        return new OrderDTO(this.id, this.customer, UserSingleton.getUser(),
                new OrderStateDTO(33, "in progress"), this.dateTime, orderItems);
    }

    private List<OrderItemDTO> generateOrderItemDTOs() {
        List<OrderItemDTO> listToReturn = new ArrayList<>();

        for (UiOrderItem uiItem : this.orderItems) {
            listToReturn.add(uiItem.getOrderDTO());
        }

        return listToReturn;
    }


    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Customer: " + this.customer.toString();
    }

    public void setId(int i) {
        this.id = i;
    }
}
