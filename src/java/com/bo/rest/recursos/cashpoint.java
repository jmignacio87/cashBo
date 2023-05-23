package com.bo.rest.recursos;

import com.bo.rest.controlador.Login;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author IGNACIO
 */
@Path("/cashpoint")
public class cashpoint {

    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getToken(String body) {
        Login login = new Login();
        return Response.ok().entity(login.singIn("cashpoint", body)).build();

    }

    @GET
    @Path("/transactions/pending")
    public Response getPendingTransactions() {
        return Response.ok().entity("").build();
    }

    @POST
    @Path("/transactiondetails")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDetailTransactions() {
        return Response.ok().entity("").build();
    }

    @POST
    @Path("/request/validate-transaction")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateTransaction() {
        return Response.ok().entity("").build();
    }

    @POST
    @Path("/gave")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response gave() {
        return Response.ok().entity("").build();
    }

}
