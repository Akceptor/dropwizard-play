package org.akceptor.resources;

import org.akceptor.core.Entity;

import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/entities")
@Produces(MediaType.APPLICATION_JSON)
public class EntitiesRESTController {
    private final Validator validator;
    private List<Entity> entities = new ArrayList<>();

    public EntitiesRESTController(Validator validator) {
        this.validator = validator;
    }

    {
        Entity e1 = new Entity(1, "Name one");
        Entity e2 = new Entity(2, "Name two");
        entities.add(e1);
        entities.add(e2);
    }

    @GET
    public Response getEntities() {
        return Response.ok(entities).build();
    }

    @POST
    public Entity createEntity(Entity e) {
        entities.add(e);
        return e;
    }
}
