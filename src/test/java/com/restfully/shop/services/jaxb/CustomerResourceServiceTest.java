package com.restfully.shop.services.jaxb;

import org.junit.Assert;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

public class CustomerResourceServiceTest {
    private final String sampleCustomer = "<customer>"
            + "<first-name>Bill</first-name>"
            + "<last-name>Burke</last-name>"
            + "<street>256 Clarendon Street</street>"
            + "<city>Boston</city>"
            + "<state>MA</state>"
            + "<zip>02115</zip>"
            + "<country>USA</country>"
            + "</customer>";
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

            Response response = client.target("http://localhost:8080/onlineshop/services/jaxb/customers/").request().post(Entity.xml(xml));
            System.out.println("HTTP Status : "+response.getStatus());
            Assert.assertTrue(response.getStatus() == 201);
            response.close();

        } finally {
            client.close();
        }

    }

    @org.junit.Test
    public void getCustomerPositive() {
        Client client = ClientBuilder.newClient();
        try {
            String xml = "<customer>"
                    + "<first-name>Bill</first-name>"
                    + "<last-name>Burke</last-name>"
                    + "<street>256 Clarendon Street</street>"
                    + "<city>Boston</city>"
                    + "<state>MA</state>"
                    + "<zip>02115</zip>"
                    + "<country>USA</country>"
                    + "</customer>";

            Response createResponse = client.target("http://localhost:8080/onlineshop/services/jaxb/customers/").request().post(Entity.xml(xml));
            String location;
            location = (String) createResponse.getHeaders().get("location").get(0);
            if (location == null || "".equals(location)) {
                Assert.fail("location of customer could not be retrived");
            }
            Response getResponse = client.target(location).request().get();
            Assert.assertTrue(getResponse.getStatus() == 200);
            String body = getResponse.readEntity(String.class);
            Assert.assertTrue(body.contains("<street>256 Clarendon Street</street>"));

        } finally {
            client.close();
        }
    }

        @org.junit.Test
        public void getCustomerNegative() {
            Client client = ClientBuilder.newClient();
            try {
                Response createResponse = client.target("http://localhost:8080/onlineshop/services/jaxb/customers/").request().post(Entity.xml(sampleCustomer));
                Response getResponse = client.target("http://localhost:8080/onlineshop/services/jaxb/customers/999999999").request().get();
                Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), getResponse.getStatus());
            }finally {
                client.close();
            }
    }

    @org.junit.Test
    public void updateCustomer() {
    }

    @org.junit.Test
    public void getAllCustomers() {
    }
}
