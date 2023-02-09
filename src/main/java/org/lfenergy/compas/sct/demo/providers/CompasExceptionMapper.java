package org.lfenergy.compas.sct.demo.providers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.lfenergy.compas.core.commons.exception.CompasException;

@Provider
public class CompasExceptionMapper implements ExceptionMapper<CompasException> {

    @Override
    public Response toResponse(CompasException exception) {
        return Response.status(400).entity(exception.getMessage()).build();
    }
}
