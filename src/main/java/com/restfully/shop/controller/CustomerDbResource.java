package com.restfully.shop.controller;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/customers")
public interface CustomerDbResource {

    @Path("{database}-db")
    CustomerResource getDatabase(@PathParam("database") String db);
}
