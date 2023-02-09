package org.lfenergy.compas.sct.demo.client;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.lfenergy.compas.sct.commons.scl.SclRootAdapter;
import org.lfenergy.compas.sct.commons.scl.SclService;
import org.lfenergy.compas.sct.demo.utils.MarshallerWrapper;
import java.util.Optional;

@Path("/v1/scl")
@Tag(name = "SCL service", description = "Get the value for a CoMPAS SCT SclService")
public class SclServiceController implements ISclService {

    @Inject
    private MarshallerWrapper marshallerWrapper;

    @Override
    public String initSCD(@QueryParam("version") String headerVersion, @QueryParam("revision") String headerRevision) {
        SclRootAdapter sclRootAdapter = SclService.initScl(Optional.empty(), headerVersion, headerRevision);
        final SCL scl = sclRootAdapter.getCurrentElem();
        return marshallerWrapper.marshall(scl);
    }

}
