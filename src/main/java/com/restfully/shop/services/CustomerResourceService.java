package com.restfully.shop.services;

import com.restfully.shop.domain.Customer;
import com.sun.jndi.toolkit.url.Uri;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;
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

public class CustomerResourceService implements CustomerResource {

    private static Map<Integer, Customer> customerDB = new ConcurrentHashMap<>();
    private static AtomicInteger idCounter = new AtomicInteger();


    public Response createCustomer(InputStream is, UriInfo uriInfo) {
        Customer customer = readCustomer(is);
        customer.setId(idCounter.incrementAndGet());
        customerDB.put(customer.getId(), customer);
        System.out.println("Created Customer : " + customer.getId());
        return Response.created(URI.create(uriInfo.getRequestUri().toString() + customer.getId())).build();
    }

    public StreamingOutput getCustomer(int id) {
        final Customer customer = customerDB.get(id);
        if (customer == null) throw new WebApplicationException(Response.Status.NOT_FOUND);
        return output -> outputCustomer(output,customer);
    }

    public void updateCustomer(int id, InputStream is) {
        Customer customer = readCustomer(is);
        if (customerDB.get(id) == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        customerDB.put(id, customer);

    }

    public Response getAllCustomers() {
        return Response.ok("List of all dummy customers").build();
    }

    private Customer readCustomer(InputStream is) {
        DocumentBuilder builder = null;
        Customer cust = null;

        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(is);
            Element root = doc.getDocumentElement();
            cust = new Customer();
            if (root.getAttribute("id") != null && !root.getAttribute("id").trim().equals("")) {
                cust.setId(Integer.valueOf(root.getAttribute("id")));
            }

            NodeList nodes = root.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                if(nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodes.item(i);

                    if (element.getTagName().equals("first-name")) {
                        cust.setFirstName(element.getTextContent());
                    } else if (element.getTagName().equals("last-name")) {
                        cust.setLastName(element.getTextContent());
                    } else if (element.getTagName().equals("street")) {
                        cust.setStreet(element.getTextContent());
                    } else if (element.getTagName().equals("city")) {
                        cust.setCity(element.getTextContent());
                    } else if (element.getTagName().equals("state")) {
                        cust.setState(element.getTextContent());
                    } else if (element.getTagName().equals("zip")) {
                        cust.setZip(element.getTextContent());
                    } else if (element.getTagName().equals("country")) {
                        cust.setCountry(element.getTextContent());
                    }
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

    private void outputCustomer(OutputStream os, Customer cust) {
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
