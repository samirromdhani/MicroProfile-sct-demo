package org.lfenergy.compas.sct.demo.providers;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.Provider;
import org.lfenergy.compas.sct.demo.model.SclModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.stream.Collectors;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class SclModelReader implements MessageBodyReader<SclModel> {

    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return type.equals(SclModel.class) && mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public SclModel readFrom(Class<SclModel> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> multivaluedMap, InputStream entityStream) throws IOException, WebApplicationException {
        String s = new BufferedReader(new InputStreamReader(entityStream)).lines().collect(Collectors.joining(" "))
                .trim();
        if(!s.startsWith("{") || !s.endsWith("}")) {
            throw new WebApplicationException(Response.status(400).build());
        }
        return new SclModel();
    }
}
