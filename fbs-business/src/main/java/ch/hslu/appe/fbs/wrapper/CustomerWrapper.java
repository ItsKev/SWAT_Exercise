package ch.hslu.appe.fbs.wrapper;

import ch.hslu.appe.fbs.common.dto.CustomerDTO;
import ch.hslu.appe.fbs.model.db.Customer;

public final class CustomerWrapper implements Wrappable<Customer, CustomerDTO> {

    @Override
    public CustomerDTO dtoFromEntity(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getPrename(),
                customer.getSurname(),
                customer.getPlz(),
                customer.getCity(),
                customer.getAdress()
        );
    }

    @Override
    public Customer entityFromDTO(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        final Customer customer = new Customer();
        if (customerDTO.getId() != -1) {
            customer.setId(customerDTO.getId());
        }
        customer.setPrename(customerDTO.getPrename());
        customer.setSurname(customerDTO.getSurname());
        customer.setAdress(customerDTO.getAddress());
        customer.setPlz(customerDTO.getPlz());
        customer.setCity(customerDTO.getCity());
        return customer;
    }
}
