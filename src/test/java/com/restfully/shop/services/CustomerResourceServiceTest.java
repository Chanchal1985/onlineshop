package com.restfully.shop.services;

import org.apache.http.protocol.HTTP;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

//@RunWith(Arquillian.class)
public class CustomerResourceServiceTest {
  /*  @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(CustomerResourceService.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }*/

    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @org.junit.Test
    public void createCustomer() {
        Client client = ClientBuilder.newClient();
        try{
            String xml = "<customer>"
                    + "<first-name>Bill</first-name>"
                    + "<last-name>Burke</last-name>"
                    + "<street>256 Clarendon Street</street>"
                    + "<city>Boston</city>"
                    + "<state>MA</state>"
                    + "<zip>02115</zip>"
                    + "<country>USA</country>"
                    + "</customer>";

            Response response = client.target("http://localhost:8080/onlineshop/services/customers/north-db/").request().post(Entity.xml(xml));

            Assert.assertTrue(response.getStatus() == 200);
            response.close();

        } finally {
            client.close();
        }

    }

    @org.junit.Test
    public void getCustomer() {
    }

    @org.junit.Test
    public void updateCustomer() {
    }

    @org.junit.Test
    public void getAllCustomers() {
    }
}
