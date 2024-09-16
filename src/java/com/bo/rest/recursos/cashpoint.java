package com.bo.rest.recursos;

import com.bo.rest.controlador.EnablepointController;
import com.bo.rest.controlador.GaveController;
import com.bo.rest.controlador.LoginController;
import com.bo.rest.controlador.TransactionDetailsController;
import com.bo.rest.controlador.TransactionsController;
import com.bo.rest.controlador.ValidateTransactionController;
import com.bo.rest.modelos.pendingModel;
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
import javax.ws.rs.core.UriInfo;

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
        LoginController login = new LoginController();
        return Response.ok().entity(login.singIn(TypeUtils.CASHPOINT, body)).build();

    }

    /*
    @GET
    @Path("/transactions/pending")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPendingTransactions(@Context HttpHeaders headers) {
        String authorization = headers.getRequestHeader("Authorization").get(0);

        if (authorization.startsWith("Bearer")) {
            TransactionsController transactions = new TransactionsController();

            return Response.ok().entity(transactions.getPendingTransactions(authorization)).build();
        }

        return Response.serverError().build();
    }
    */
    @GET
    @Path("/transactions/pending")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPendingTransactions(@Context HttpHeaders headers, @Context UriInfo parameters) {
        String authorization = headers.getRequestHeader("Authorization").get(0);
        pendingModel model = new pendingModel();

        if (authorization.startsWith("Bearer")) {
            TransactionsController transactions = new TransactionsController();

            model.setBank_id(parameters.getQueryParameters().getFirst("bank_id"));
            
            return Response.ok().entity(transactions.getPendingTransactions(authorization, model.getBank_id())).build();
            
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
            TransactionDetailsController transactionDetails = new TransactionDetailsController();

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
    
    @POST
    @Path("/enablepoint")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response enablepoint(@Context HttpHeaders headers, String body){
       
        String authorization = headers.getRequestHeader("Authorization").get(0);
        
        if (authorization.startsWith("Bearer")){
            EnablepointController enablepointController = new EnablepointController();
            return Response.ok().entity(enablepointController.getEnablepointRequest(authorization, body)).build();
        }
        return Response.serverError().build();
    }
    

}
