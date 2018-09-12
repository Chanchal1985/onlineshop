package com.restfully.shop.services.jaxb;

import com.restfully.shop.domain.jaxb.Customer;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/jaxb/customers")
public interface CustomerResource {

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    Response createCustomer(Customer customer, @Context UriInfo uriInfo);

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    Customer getCustomer(@PathParam("id") int id);

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void updateCustomer(@PathParam("id") int id, Customer customer);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getAllCustomers();
}
