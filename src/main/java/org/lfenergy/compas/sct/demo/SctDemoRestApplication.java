package org.lfenergy.compas.sct.demo;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@ApplicationPath("/api")
@OpenAPIDefinition(info = @Info(
        title = "MicroProfile SCT application",
        version = "1.0.0"),
        servers = {@Server(url = "", description = "localhost")})
public class SctDemoRestApplication extends Application {
}
