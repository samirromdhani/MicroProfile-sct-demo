package org.lfenergy.compas.sct.demo.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@Path("/v1/scl")
public interface ISclService {

    @GET
    @Path("/init")
    @Produces(MediaType.APPLICATION_XML)
    @Operation(description = "Init SCL object for this version and revision")
    String initSCD(@QueryParam("version") String headerVersion, @QueryParam("revision") String headerRevision);
}
