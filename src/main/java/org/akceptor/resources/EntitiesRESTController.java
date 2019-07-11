package org.akceptor.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.IntParam;
import org.akceptor.core.Thing;
import org.akceptor.db.ThingDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/things")
@Produces(MediaType.APPLICATION_JSON)
public class EntitiesRESTController {
    private final ThingDAO thingDAO;

    public EntitiesRESTController(ThingDAO thingDAO) {
        this.thingDAO = thingDAO;
    }

    @GET
    @UnitOfWork
    public Response getEntities() {
        return Response.ok(thingDAO.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @Timed
    @UnitOfWork
    public Optional<Thing> findPerson(@PathParam("id") IntParam id) {
        return thingDAO.findById(id.get());
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    public Thing createEntity(Thing e) {
        return thingDAO.create(e);
    }
}
