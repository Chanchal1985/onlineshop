package com.restfully.shop.config;

import com.restfully.shop.services.CustomerDbResourceService;
import com.restfully.shop.services.CustomerResourceService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/services")
public class ShoppingApplication extends Application {

    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> empty = new HashSet<>();

    public ShoppingApplication() {
        singletons.add(new CustomerDbResourceService());
        singletons.add(new com.restfully.shop.services.jaxb.CustomerResourceService());
    }


    @Override
    public Set<Class<?>> getClasses() {
        return empty;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
