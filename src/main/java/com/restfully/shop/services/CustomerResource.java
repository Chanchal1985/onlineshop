package com.restfully.shop.services;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.InputStream;


public interface CustomerResource {

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    Response createCustomer(InputStream is, @Context UriInfo uriInfo);

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    StreamingOutput getCustomer(@PathParam("id") int id);

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void updateCustomer( @PathParam("id") int id, InputStream is);

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getAllCustomers();
}
