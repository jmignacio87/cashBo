package com.bo.rest.recursos;

import com.bo.rest.controlador.Login;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author IGNACIO
 */
@Path("/{source}")
public class cashpoint {

    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getToken(@PathParam("source") String source, String body) {
        Login login = new Login();
        return Response.ok().entity(login.singIn(source, body)).build();
    }

}
