package com.restfully.shop.controller.jaxb;

import com.restfully.shop.domain.jaxb.Address;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/jaxb/addresses")
public interface AddressResource {



    @POST
    @Consumes("application/example-java")
    Response createAddress(Address address, @Context UriInfo uriInfo);

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    Address getAddress(@PathParam("id") int id);

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void updateAddress(@PathParam("id") int id, Address address);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Address> getAllAddresses();
}
