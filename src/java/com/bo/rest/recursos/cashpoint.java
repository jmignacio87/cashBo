package com.bo.rest.recursos;

import com.bo.rest.controlador.GaveController;
import com.bo.rest.controlador.Login;
import com.bo.rest.controlador.TransactionDetails;
import com.bo.rest.controlador.Transactions;
import com.bo.rest.controlador.ValidateTransactionController;
import com.bo.rest.utils.TypeUtils;

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
        return Response.ok().entity(login.singIn(TypeUtils.CASHPOINT, body)).build();

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
    public Response getDetailTransactions(@Context HttpHeaders headers, String body) {
        String authorization = headers.getRequestHeader("Authorization").get(0);

        if (authorization.startsWith("Bearer")) {
            TransactionDetails transactionDetails = new TransactionDetails();

            return Response.ok().entity(transactionDetails.getTransactionDetail(authorization, body)).build();
        }

        return Response.serverError().build();
    }

    @POST
    @Path("/request/validate-transaction")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateTransaction(@Context HttpHeaders headers, String body) {
        String authorization = headers.getRequestHeader("Authorization").get(0);

        if (authorization.startsWith("Bearer")) {
            ValidateTransactionController controller = new ValidateTransactionController();

            return Response.ok().entity(controller.getValidateTransactionRequest(authorization, body)).build();
        }

        return Response.serverError().build();
    }

    @POST
    @Path("/gave")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response gave(@Context HttpHeaders headers, String body) {

        String authorization = headers.getRequestHeader("Authorization").get(0);

        if (authorization.startsWith("Bearer")) {
            GaveController gaveController = new GaveController();

            return Response.ok().entity(gaveController.getGaveRequest(authorization, body)).build();
        }

        return Response.serverError().build();
    }

}
