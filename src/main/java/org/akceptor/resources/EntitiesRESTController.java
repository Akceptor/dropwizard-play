package org.akceptor.resources;

import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Path("/entities")
@Produces(MediaType.APPLICATION_JSON)
public class EntitiesRESTController {
    private final Validator validator;

    public EntitiesRESTController(Validator validator) {
        this.validator = validator;
    }

    @GET
    public Response getEntities() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "User one");
        map.put("2", "User two");
        return Response.ok(map).build();
    }
}
