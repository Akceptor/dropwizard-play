package org.akceptor.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.IntParam;
import org.akceptor.core.Child;
import org.akceptor.core.Thing;
import org.akceptor.db.ThingDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Optional;

@Path("/things")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ThingsRESTController {
    private final ThingDAO thingDAO;

    public ThingsRESTController(ThingDAO thingDAO) {
        this.thingDAO = thingDAO;
    }

    @GET
    @UnitOfWork
    public Response getThings() {
        return Response.ok(thingDAO.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @Timed
    @UnitOfWork
    public Optional<Thing> findThing(@PathParam("id") IntParam id) {
        return thingDAO.findById(id.get());
    }

    @POST
    @UnitOfWork
    public Thing createThing(Thing thing) {
        return thingDAO.create(thing);
    }
}
