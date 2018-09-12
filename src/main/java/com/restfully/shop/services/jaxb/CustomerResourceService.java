package com.restfully.shop.services.jaxb;

import com.restfully.shop.domain.jaxb.Customer;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerResourceService implements CustomerResource {

    private static Map<Integer, Customer> customerDB = new ConcurrentHashMap<>();
    private static AtomicInteger idCounter = new AtomicInteger();

    public Response createCustomer(Customer customer, UriInfo uriInfo) {
        customer.setId(idCounter.incrementAndGet());
        customerDB.put(customer.getId(), customer);
        System.out.println("Created Customer : " + customer.getId());
        return Response.created(URI.create(uriInfo.getRequestUri().toString() + customer.getId())).build();
    }

    public Customer getCustomer(int id) {
        final Customer customer = customerDB.get(id);
        if (customer == null) throw new WebApplicationException(Response.Status.NOT_FOUND);
        return customer;
    }

    public void updateCustomer(int id, Customer customer) {
        if (customerDB.get(id) == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        customerDB.put(id, customer);
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customerDB.values());
    }
}
