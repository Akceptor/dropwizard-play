package org.akceptor.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.IntParam;
import org.akceptor.core.Child;
import org.akceptor.core.Thing;
import org.akceptor.db.ChildDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/children")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChildrenRESTController {
    private final ChildDAO childDAO;

    public ChildrenRESTController(ChildDAO childDAO) {
        this.childDAO = childDAO;
    }

    @GET
    @UnitOfWork
    public Response getChildren() {
        return Response.ok(childDAO.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @Timed
    @UnitOfWork
    public Optional<Child> findChild(@PathParam("id") IntParam id) {
        return childDAO.findById(id.get());
    }

    @POST
    @UnitOfWork
    public Child createChild(Child child) {
        return childDAO.create(child);
    }
}
