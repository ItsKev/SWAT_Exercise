package ch.hslu.appe.fbs.business.order;

import ch.hslu.appe.fbs.business.authorisation.AuthorisationManager;
import ch.hslu.appe.fbs.business.bill.BillManager;
import ch.hslu.appe.fbs.business.bill.BillManagerFactory;
import ch.hslu.appe.fbs.business.item.ItemManager;
import ch.hslu.appe.fbs.business.item.ItemManagerFactory;
import ch.hslu.appe.fbs.business.logger.Logger;
import ch.hslu.appe.fbs.common.dto.*;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.permission.UserPermissions;
import ch.hslu.appe.fbs.data.order.OrderPersistor;
import ch.hslu.appe.fbs.data.order.OrderPersistorFactory;
import ch.hslu.appe.fbs.data.orderitem.OrderItemPersistor;
import ch.hslu.appe.fbs.data.orderitem.OrderItemPersistorFactory;
import ch.hslu.appe.fbs.data.orderstate.OrderStatePersistor;
import ch.hslu.appe.fbs.data.orderstate.OrderStatePersistorFactory;
import ch.hslu.appe.fbs.data.orderstate.OrderStates;
import ch.hslu.appe.fbs.model.db.Bill;
import ch.hslu.appe.fbs.model.db.Order;
import ch.hslu.appe.fbs.model.db.OrderItem;
import ch.hslu.appe.fbs.model.db.OrderState;
import ch.hslu.appe.fbs.wrapper.OrderItemWrapper;
import ch.hslu.appe.fbs.wrapper.OrderWrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderManagerImpl implements OrderManager {

    private static final Object LOCK = new Object();

    private final OrderPersistor orderPersistor;
    private final OrderItemPersistor orderItemPersistor;
    private final OrderStatePersistor orderStatePersistor;
    private final OrderWrapper orderWrapper;
    private final OrderItemWrapper orderItemWrapper;
    private final ItemManager itemManager;
    private final BillManager billManager;

    public OrderManagerImpl() {
        this.orderPersistor = OrderPersistorFactory.createOrderPersistor();
        this.orderItemPersistor = OrderItemPersistorFactory.createOrderItemPersistor();
        this.orderStatePersistor = OrderStatePersistorFactory.createOrderStatePersistor();
        this.orderWrapper = new OrderWrapper();
        this.orderItemWrapper = new OrderItemWrapper();
        this.itemManager = ItemManagerFactory.getItemManager();
        this.billManager = BillManagerFactory.getBillManager();
    }

    @Override
    public List<OrderDTO> getOrders(int customerId, UserDTO userDTO) throws UserNotAuthorisedException {
        AuthorisationManager.checkUserAuthorisation(userDTO, UserPermissions.GET_ORDER);
        synchronized (LOCK) {
            List<OrderDTO> orders = new ArrayList<>();
            this.orderPersistor.getByCustomer(customerId).forEach(order -> orders.add(orderWrapper.dtoFromEntity(order)));
            return orders;
        }
    }

    @Override
    public List<OrderDTO> getOrders(CustomerDTO customer, UserDTO userDTO) throws UserNotAuthorisedException {
        return this.getOrders(customer.getId(), userDTO);
    }

    @Override
    public List<OrderDTO> getOrdersByOrderState(int customerId, OrderStateDTO orderState, UserDTO userDTO) throws UserNotAuthorisedException {
        if (orderState == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        AuthorisationManager.checkUserAuthorisation(userDTO, UserPermissions.GET_ORDER);
        synchronized (LOCK) {
            List<OrderDTO> orders = new ArrayList<>();
            this.orderPersistor.getByCustomerAndOrderState(customerId, orderState.getId())
                    .forEach(order -> orders.add(orderWrapper.dtoFromEntity(order)));
            return orders;
        }
    }

    @Override
    public void createOrder(OrderDTO order, UserDTO userDTO) throws UserNotAuthorisedException {
        if (order == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        AuthorisationManager.checkUserAuthorisation(userDTO, UserPermissions.CREATE_ORDER);
        synchronized (LOCK) {
            final Order persOrder = this.orderWrapper.entityFromDTO(order);
            this.orderPersistor.save(persOrder);
            final Collection<OrderItem> persOrderItems = persOrder.getOrderItemsById();
            final AtomicInteger price = new AtomicInteger();
            order.getOrderItems().forEach(orderItemDTO -> {
                final OrderItem orderItem = this.orderItemWrapper.entityFromDTO(orderItemDTO);
                orderItem.setOrderId(persOrder.getId());
                persOrderItems.add(orderItem);
                final ItemDTO item = this.itemManager.getItem(orderItemDTO.getItemByItemId().getId());
                this.itemManager.updateItemStock(item, orderItemDTO.getQuantity());
                price.addAndGet(orderItemDTO.getPrice());
            });
            persOrderItems.forEach(this.orderItemPersistor::save);
            final Bill bill = this.billManager.generateBill(persOrder.getId(), price.get());
            persOrder.getBillsById().add(bill);
            this.orderPersistor.update(persOrder);

            Logger.logInfo(userDTO.getUserName(), "Created a new Order with Id: " + persOrder.getId());
        }
    }

    @Override
    public void cancelOrder(OrderDTO order, UserDTO userDTO) throws UserNotAuthorisedException {
        if (order == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        AuthorisationManager.checkUserAuthorisation(userDTO, UserPermissions.CANCEL_ORDER);
        synchronized (LOCK) {
            final Order persOrder = this.orderWrapper.entityFromDTO(order);
            final Optional<OrderState> optOrderState = this.orderStatePersistor.getByState(OrderStates.CANCELLED.getState());
            if (optOrderState.isPresent()) {
                final OrderState osCancelled = optOrderState.get();
                persOrder.setOrderState(osCancelled.getId());
                this.orderPersistor.update(persOrder);
                order.getOrderItems().forEach(orderItemDTO ->
                        this.itemManager.refillItemStock(orderItemDTO.getItemByItemId(), orderItemDTO.getQuantity()));
            }
            Logger.logInfo(userDTO.getUserName(), "Canceled Order with Id: " + order.getId());
        }
    }

    @Override
    public void updateOrder(OrderDTO order, UserDTO userDTO) throws UserNotAuthorisedException {
        if (order == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        AuthorisationManager.checkUserAuthorisation(userDTO, UserPermissions.EDIT_ORDER);
        synchronized (LOCK) {
            final Order persOrder = this.orderWrapper.entityFromDTO(order);
            final Optional<Order> oldOrder = this.orderPersistor.getById(persOrder.getId());
            if (oldOrder.isPresent()) {
                OrderDTO orderDTO = this.orderWrapper.dtoFromEntity(oldOrder.get());
                Optional<OrderState> optOrderState = this.orderStatePersistor.getByState(OrderStates.CANCELLED.getState());
                if (optOrderState.isPresent()) {
                    if (oldOrder.get().getOrderStateByOrderState().equals(optOrderState.get())) {
                        orderDTO.getOrderItems().forEach(orderItemDTO ->
                                this.itemManager.refillItemStock(orderItemDTO.getItemByItemId(), orderItemDTO.getQuantity()));
                    }
                }
            }
            order.getOrderItems().forEach(orderItemDTO ->
                    this.itemManager.updateItemStock(orderItemDTO.getItemByItemId(), orderItemDTO.getQuantity()));
            this.orderPersistor.update(persOrder);
        }
    }

    @Override
    public List<OrderDTO> getAllOrders(UserDTO userDTO) throws UserNotAuthorisedException {
        AuthorisationManager.checkUserAuthorisation(userDTO, UserPermissions.GET_ALL_ORDERS);
        List<OrderDTO> orders = new ArrayList<>();
        synchronized (LOCK) {
            this.orderPersistor.getAll().forEach(order -> orders.add(this.orderWrapper.dtoFromEntity(order)));
        }
        return orders;
    }
}
