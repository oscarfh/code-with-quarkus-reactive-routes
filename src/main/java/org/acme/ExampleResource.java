package org.acme;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.common.MyCustomException;
import org.common.User;
import org.common.UserService;

@Path("/hello")
public class ExampleResource {

    private UserService service;

    @Inject
    public ExampleResource(UserService service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User hello(@QueryParam("name") String name) {
        return service.createUser(name);
    }
}