package com.restfully.shop.utility;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Produces("application/example-java")
@Consumes("application/example-java")
public class JavaMarshaller implements MessageBodyWriter , MessageBodyReader {
    @Override
    public boolean isReadable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Serializable.class.isAssignableFrom(type);
    }

    @Override
    public Object readFrom(Class type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        ObjectInputStream inputStream = new ObjectInputStream(entityStream);
        try {
            return inputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException();
        } catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean isWriteable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Serializable.class.isAssignableFrom(type);
    }

    @Override
    public void writeTo(Object o, Class type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        ObjectOutputStream oos = new ObjectOutputStream(entityStream);
        oos.writeObject(o);
    }

    @Override
    public long getSize(Object o, Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }
}
