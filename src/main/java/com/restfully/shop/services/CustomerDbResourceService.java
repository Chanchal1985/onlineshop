package com.restfully.shop.services;

public class CustomerDbResourceService implements CustomerDbResource {

    @Override
    public CustomerResource getDatabase(String db) {
        CustomerResource resource = locateDbResource(db);
        return resource;
    }

    private CustomerResource locateDbResource(String db) {
        return new CustomerResourceService();
    }
}
