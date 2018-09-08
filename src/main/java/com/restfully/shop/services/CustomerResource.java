package com.restfully.shop.services;

import com.restfully.shop.domain.Customer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
@Path("/customers")
public class CustomerResource {

    private Map<Integer, Customer> customerDB = new ConcurrentHashMap<>();
    private AtomicInteger idCounter = new AtomicInteger();


    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response createCustomer(InputStream is){
        Customer customer = readCustomer(is);
        customer.setId(idCounter.incrementAndGet());
        customerDB.put(customer.getId(),customer);
        System.out.println("Created Customer : "+customer.getId());
        return Response.created(URI.create("/customers/"+customer.getId())).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public StreamingOutput getCustomer(@PathParam("id") int id){
        final Customer customer = customerDB.get(id);
        if(customer == null){
            new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return new StreamingOutput() {
            @Override
            public void write(OutputStream output) throws IOException, WebApplicationException {
                outputCustomer(output, customer);
            }
        };
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateCustomer( @PathParam("id") int id, InputStream is){
        Customer customer = readCustomer(is);
        if(customerDB.get(id) == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        customerDB.put(id, customer);

    }

    private Customer readCustomer(InputStream is) {
        DocumentBuilder builder = null;
        Customer cust = null ;

        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(is);
            Element root = doc.getDocumentElement();
            cust = new Customer();
            if(root.getAttribute("id") != null  && !root.getAttribute("id").trim().equals("")){
                cust.setId(Integer.valueOf(root.getAttribute("id")));
            }

            NodeList nodes = root.getChildNodes();
            for (int i = 0 ;i< nodes.getLength();i++){
                Element element = (Element) nodes.item(i);
                if(element.getTagName().equals("first-name")){
                    cust.setFirstName(element.getTextContent());
                }else if (element.getTagName().equals("last-name")){
                    cust.setLastName(element.getTextContent());
                }else if (element.getTagName().equals("street")){
                    cust.setStreet(element.getTextContent());
                }else if (element.getTagName().equals("city")){
                    cust.setCity(element.getTextContent());
                }else if (element.getTagName().equals("state")){
                    cust.setState(element.getTextContent());
                }else if (element.getTagName().equals("zip")){
                    cust.setZip(element.getTextContent());
                }else if (element.getTagName().equals("country")){
                    cust.setCountry(element.getTextContent());
                }
            }

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return cust;
    }

    private void outputCustomer(OutputStream os, Customer cust){
        PrintStream writer = new PrintStream(os);
        writer.println("<customer id=\"" + cust.getId() + "\">");
        writer.println("<first-name>" + cust.getFirstName()
                + "</first-name>");
        writer.println("<last-name>" + cust.getLastName()
                + "</last-name>");
        writer.println("<street>" + cust.getStreet() + "</street>");
        writer.println("<city>" + cust.getCity() + "</city>");
        writer.println("<state>" + cust.getState() + "</state>");
        writer.println("<zip>" + cust.getZip() + "</zip>");
        writer.println("<country>" + cust.getCountry() + "</country>");
        writer.println("</customer>");
    }

}
