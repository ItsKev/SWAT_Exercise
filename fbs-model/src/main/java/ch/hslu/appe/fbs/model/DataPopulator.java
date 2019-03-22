package ch.hslu.appe.fbs.model;

import ch.hslu.appe.fbs.model.db.*;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

final class DataPopulator {

    private final EntityManager entityManager;

    private List<User> users;
    private List<UserRole> userRoles;
    private List<Customer> customers;
    private List<Item> items;
    private List<OrderState> orderStates;
    private List<Order> orders;
    private List<OrderItem> orderItems;
    private List<Bill> bills;

    DataPopulator() {
        this.entityManager = PersistenceUnit.getEntityManager();
    }

    void insertSampleDataIntoDB() {
        createUserRoles();
        createUsers();
        createCustomers();
        createItems();
        createOrderStates();
        createOrders();
        createOrderItems();
        createReorders();
        createBills();
        createReminders();
    }

    private void createReminders() {
        final List<Reminder> reminders = new ArrayList<>();

        final Reminder reminder1 = new Reminder();
        reminder1.setBillId(this.bills.get(0).getId());
        reminders.add(reminder1);

        final Reminder reminder2 = new Reminder();
        reminder2.setBillId(this.bills.get(1).getId());
        reminders.add(reminder2);

        final Reminder reminder3 = new Reminder();
        reminder3.setBillId(this.bills.get(2).getId());
        reminders.add(reminder3);

        final Reminder reminder4 = new Reminder();
        reminder4.setBillId(this.bills.get(3).getId());
        reminders.add(reminder4);

        persist(reminders);
    }

    private void createBills() {
        this.bills = new ArrayList<>();

        for (Order order : this.orders) {
            final AtomicReference<Integer> price = new AtomicReference<>((Integer) 0);
            final int orderId = order.getId();
            final Bill bill = new Bill();
            bill.setOrderId(orderId);
            this.orderItems.forEach(orderItem -> {
                if (orderItem.getOrderId().equals(orderId)) {
                    price.updateAndGet(v -> v + orderItem.getPrice());
                }
            });
            bill.setPrice(price.get());
            this.bills.add(bill);
        }

        persist(this.bills);
    }

    private void createReorders() {
        final List<Reorder> reorders = new ArrayList<>();

        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date reorderDate = new Date();
        Date deliveredDate = new Date();
        try {
            reorderDate = format.parse("2018-04-30");
            deliveredDate = reorderDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final Reorder reorder1 = new Reorder();
        reorder1.setItemId(this.items.get(4).getId());
        reorder1.setQuantity(30);
        reorder1.setReorderDate(new Timestamp(reorderDate.getTime()));
        reorder1.setDelivered(new Timestamp(deliveredDate.getTime()));
        reorders.add(reorder1);

        persist(reorders);
    }

    private void createOrderItems() {
        this.orderItems = new ArrayList<>();

        final OrderItem orderItem1 = new OrderItem();
        orderItem1.setItemId(this.items.get(0).getId());
        orderItem1.setOrderId(this.orders.get(0).getId());
        orderItem1.setPrice(45);
        orderItem1.setQuantity(1);
        this.orderItems.add(orderItem1);

        final OrderItem orderItem2 = new OrderItem();
        orderItem2.setItemId(this.items.get(1).getId());
        orderItem2.setOrderId(this.orders.get(0).getId());
        orderItem2.setPrice(178);
        orderItem2.setQuantity(1);
        this.orderItems.add(orderItem2);

        final OrderItem orderItem3 = new OrderItem();
        orderItem3.setItemId(this.items.get(2).getId());
        orderItem3.setOrderId(this.orders.get(1).getId());
        orderItem3.setPrice(370);
        orderItem3.setQuantity(1);
        this.orderItems.add(orderItem3);

        final OrderItem orderItem4 = new OrderItem();
        orderItem4.setItemId(this.items.get(3).getId());
        orderItem4.setOrderId(this.orders.get(2).getId());
        orderItem4.setPrice(220);
        orderItem4.setQuantity(1);
        this.orderItems.add(orderItem4);

        final OrderItem orderItem5 = new OrderItem();
        orderItem5.setItemId(this.items.get(0).getId());
        orderItem5.setOrderId(this.orders.get(3).getId());
        orderItem5.setPrice(45);
        orderItem5.setQuantity(1);
        this.orderItems.add(orderItem5);

        final OrderItem orderItem6 = new OrderItem();
        orderItem6.setItemId(this.items.get(3).getId());
        orderItem6.setOrderId(this.orders.get(4).getId());
        orderItem6.setPrice(220);
        orderItem6.setQuantity(1);
        this.orderItems.add(orderItem6);

        final OrderItem orderItem7 = new OrderItem();
        orderItem7.setItemId(this.items.get(1).getId());
        orderItem7.setOrderId(this.orders.get(5).getId());
        orderItem7.setPrice(178);
        orderItem7.setQuantity(1);
        this.orderItems.add(orderItem7);

        final OrderItem orderItem8 = new OrderItem();
        orderItem8.setItemId(this.items.get(0).getId());
        orderItem8.setOrderId(this.orders.get(6).getId());
        orderItem8.setPrice(45);
        orderItem8.setQuantity(1);
        this.orderItems.add(orderItem8);

        final OrderItem orderItem9 = new OrderItem();
        orderItem9.setItemId(this.items.get(1).getId());
        orderItem9.setOrderId(this.orders.get(7).getId());
        orderItem9.setPrice(178);
        orderItem9.setQuantity(1);
        this.orderItems.add(orderItem9);

        persist(this.orderItems);
    }

    private void createOrders() {
        this.orders = new ArrayList<>();

        final Order order1 = new Order();
        order1.setCustomerId(this.customers.get(0).getId());
        order1.setDateTime(new Timestamp(new Date().getTime()));
        order1.setOrderState(this.orderStates.get(0).getId());
        order1.setUserId(this.users.get(2).getId());
        this.orders.add(order1);

        final Order order2 = new Order();
        order2.setCustomerId(this.customers.get(1).getId());
        order2.setDateTime(new Timestamp(new Date().getTime()));
        order2.setOrderState(this.orderStates.get(1).getId());
        order2.setUserId(this.users.get(2).getId());
        this.orders.add(order2);

        final Order order3 = new Order();
        order3.setCustomerId(this.customers.get(0).getId());
        order3.setDateTime(new Timestamp(new Date().getTime()));
        order3.setOrderState(this.orderStates.get(2).getId());
        order3.setUserId(this.users.get(2).getId());
        this.orders.add(order3);

        final Order order4 = new Order();
        order4.setCustomerId(this.customers.get(2).getId());
        order4.setDateTime(new Timestamp(new Date().getTime()));
        order4.setOrderState(this.orderStates.get(0).getId());
        order4.setUserId(this.users.get(2).getId());
        this.orders.add(order4);

        final Order order5 = new Order();
        order5.setCustomerId(this.customers.get(3).getId());
        order5.setDateTime(new Timestamp(new Date().getTime()));
        order5.setOrderState(this.orderStates.get(3).getId());
        order5.setUserId(this.users.get(3).getId());
        this.orders.add(order5);

        final Order order6 = new Order();
        order6.setCustomerId(this.customers.get(1).getId());
        order6.setDateTime(new Timestamp(new Date().getTime()));
        order6.setOrderState(this.orderStates.get(2).getId());
        order6.setUserId(this.users.get(3).getId());
        this.orders.add(order6);

        final Order order7 = new Order();
        order7.setCustomerId(this.customers.get(0).getId());
        order7.setDateTime(new Timestamp(new Date().getTime()));
        order7.setOrderState(this.orderStates.get(0).getId());
        order7.setUserId(this.users.get(3).getId());
        this.orders.add(order7);

        final Order order8 = new Order();
        order8.setCustomerId(this.customers.get(2).getId());
        order8.setDateTime(new Timestamp(new Date().getTime()));
        order8.setOrderState(this.orderStates.get(2).getId());
        order8.setUserId(this.users.get(3).getId());
        this.orders.add(order8);

        persist(this.orders);
    }

    private void createOrderStates() {
        this.orderStates = new ArrayList<>();

        final OrderState state1 = new OrderState();
        state1.setState("received");
        this.orderStates.add(state1);

        final OrderState state2 = new OrderState();
        state2.setState("in progress");
        this.orderStates.add(state2);

        final OrderState state3 = new OrderState();
        state3.setState("wait for item");
        this.orderStates.add(state3);

        final OrderState state4 = new OrderState();
        state4.setState("cancelled");
        this.orderStates.add(state4);

        final OrderState state5 = new OrderState();
        state5.setState("completed");
        this.orderStates.add(state5);

        persist(this.orderStates);
    }

    private void createItems() {
        this.items = new ArrayList<>();

        final Item item1 = new Item();
        item1.setName("Sandisk Ultra Flair (128GB, USB 3.0, 128 bit)");
        item1.setArtNr("A000193");
        item1.setPrice(45);
        item1.setLocalStock(20);
        item1.setMinLocalStock(10);
        item1.setVirtualLocalStock(20);
        this.items.add(item1);

        final Item item2 = new Item();
        item2.setName("Samsung SSD 850 PRO, 512GB");
        item2.setArtNr("A000235");
        item2.setPrice(178);
        item2.setLocalStock(40);
        item2.setMinLocalStock(5);
        item2.setVirtualLocalStock(40);
        this.items.add(item2);

        final Item item3 = new Item();
        item3.setName("Intel Core i7-8700K");
        item3.setArtNr("A000245");
        item3.setPrice(370);
        item3.setLocalStock(55);
        item3.setMinLocalStock(20);
        item3.setVirtualLocalStock(55);
        this.items.add(item3);

        final Item item4 = new Item();
        item4.setName("ASUS ROG STRIX Z370-F GAMING");
        item4.setArtNr("A000888");
        item4.setPrice(220);
        item4.setLocalStock(30);
        item4.setMinLocalStock(10);
        item4.setVirtualLocalStock(30);
        this.items.add(item4);

        final Item item5 = new Item();
        item5.setName("HyperX Fury (2x, 8GB, DDR4-2400, DIMM 288)");
        item5.setArtNr("A000445");
        item5.setPrice(192);
        item5.setLocalStock(8);
        item5.setMinLocalStock(10);
        item5.setVirtualLocalStock(8);
        this.items.add(item5);

        persist(this.items);
    }

    private void createCustomers() {
        this.customers = new ArrayList<>();

        final Customer customer1 = new Customer();
        customer1.setPrename("Hans");
        customer1.setSurname("Müller");
        customer1.setAdress("Marktgasse 8");
        customer1.setPlz(3011);
        customer1.setCity("Bern");
        this.customers.add(customer1);

        final Customer customer2 = new Customer();
        customer2.setPrename("Petra");
        customer2.setSurname("Meier");
        customer2.setAdress("Bundesplatz 12");
        customer2.setPlz(6003);
        customer2.setCity("Luzern");
        this.customers.add(customer2);

        final Customer customer3 = new Customer();
        customer3.setPrename("Josef");
        customer3.setSurname("Gassmann");
        customer3.setAdress("Bahnhofstrasse 7");
        customer3.setPlz(6210);
        customer3.setCity("Sursee");
        this.customers.add(customer3);

        final Customer customer4 = new Customer();
        customer4.setPrename("Nadja");
        customer4.setSurname("Wüest");
        customer4.setAdress("Limmatstrasse 188");
        customer4.setPlz(8005);
        customer4.setCity("Zürich");
        this.customers.add(customer4);

        persist(this.customers);
    }

    private void createUsers() {
        this.users = new ArrayList<>();

        final User user1 = new User();
        user1.setDeleted((byte) 0);
        user1.setUserName("zhadm");
        user1.setPassword("sysadm");
        user1.setUserRole(this.userRoles.get(0).getId());
        this.users.add(user1);

        final User user2 = new User();
        user2.setDeleted((byte) 0);
        user2.setUserName("zhdat");
        user2.setPassword("datatypist");
        user2.setUserRole(this.userRoles.get(1).getId());
        this.users.add(user2);

        final User user3 = new User();
        user3.setDeleted((byte) 0);
        user3.setUserName("zhven_akv");
        user3.setPassword("vendorakv");
        user3.setUserRole(this.userRoles.get(2).getId());
        this.users.add(user3);

        final User user4 = new User();
        user4.setDeleted((byte) 0);
        user4.setUserName("zhven_acd");
        user4.setPassword("vendoracd");
        user4.setUserRole(this.userRoles.get(2).getId());
        this.users.add(user4);

        final User user5 = new User();
        user5.setDeleted((byte) 0);
        user5.setUserName("zhbrman");
        user5.setPassword("branchmanager");
        user5.setUserRole(this.userRoles.get(3).getId());
        this.users.add(user5);

        persist(this.users);
    }

    private void createUserRoles() {
        this.userRoles = new ArrayList<>();

        final UserRole sysadm = new UserRole();
        sysadm.setId(1);
        sysadm.setRoleName("SysAdmin");
        this.userRoles.add(sysadm);

        final UserRole datatypist = new UserRole();
        datatypist.setId(2);
        datatypist.setRoleName("Datentypist");
        this.userRoles.add(datatypist);

        final UserRole vendors = new UserRole();
        vendors.setId(3);
        vendors.setRoleName("Verkaufspersonal");
        this.userRoles.add(vendors);

        final UserRole branchManager = new UserRole();
        branchManager.setId(4);
        branchManager.setRoleName("Filialleiter");
        this.userRoles.add(branchManager);

        persist(this.userRoles);
    }

    private <T> void persist(final List<T> objects) {
        this.entityManager.getTransaction().begin();
        for (T object : objects) {
            this.entityManager.persist(object);
        }
        this.entityManager.getTransaction().commit();
    }
}
