package com.restfully.shop.controller.jaxb;

import com.restfully.shop.domain.jaxb.Address;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AddressResourceService implements  AddressResource {

    private static Map<Integer, Address> addressDB = new ConcurrentHashMap<>();
    private static AtomicInteger idCounter = new AtomicInteger();

    @Override
    public Response createAddress(Address address, UriInfo uriInfo) {
        address.setId(idCounter.incrementAndGet());
        addressDB.put(address.getId(),address);
        return Response.created(URI.create(uriInfo.getRequestUri().toString()+address.getId())).build();
    }

    @Override
    public Address getAddress(int id) {
        Address address = addressDB.get(id);
        if(address == null) throw new WebApplicationException(Response.Status.NOT_FOUND);
        return address;
    }

    @Override
    public void updateAddress(int id, Address address) {
        if(addressDB.get(id) == null) throw new WebApplicationException(Response.Status.NOT_FOUND);
        addressDB.put(id,address);
    }

    @Override
    public List<Address> getAllAddresses() {
        return new ArrayList<>(addressDB.values());
    }
}
