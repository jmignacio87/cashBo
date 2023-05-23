package com.bo.rest.recursos;

import com.bo.rest.controlador.Login;
import com.bo.rest.controlador.Transactions;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPendingTransactions(@Context HttpHeaders headers) {
        String authorization = headers.getRequestHeader("Authorization").get(0);

        if (authorization.startsWith("Bearer")) {
            Transactions transactions = new Transactions();

            return Response.ok().entity(transactions.getPendingTransactions(authorization)).build();
        }

        return Response.serverError().build();
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
