package com.restfully.shop.config;

import com.restfully.shop.controller.CustomerDbResourceService;
import com.restfully.shop.controller.jaxb.AddressResourceService;
import com.restfully.shop.utility.JavaMarshaller;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/controller")
public class ShoppingApplication extends Application {

    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> classes = new HashSet<>();

    public ShoppingApplication() {
        // add jax rs services
        singletons.add(new CustomerDbResourceService());
        singletons.add(new com.restfully.shop.controller.jaxb.CustomerResourceService());
        //singletons.add(new AddressResourceService());

        // add jax rs provides
        //classes.add(JavaMarshaller.class);
    }


    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
