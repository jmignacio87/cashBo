/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.recursos;

import com.bo.rest.controlador.ConfigurationController;
import com.bo.rest.controlador.LoginController;
import com.bo.rest.controlador.SearchNearbyController;
import com.bo.rest.modelos.ConfigurationModel;
import com.bo.rest.modelos.SearchNearbyModel;
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
 * @author aarauco2608
 */
@Path("/partner")
public class partner {

    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getToken(String body) {
        LoginController login = new LoginController();
        return Response.ok().entity(login.singIn(TypeUtils.PARTNER, body)).build();

    }

    @GET
    @Path("/cashpoints/search-nearby")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchNearby(@Context HttpHeaders headers, @Context UriInfo parameters) {

        String authorization = headers.getRequestHeader("Authorization").get(0);

        if (authorization.startsWith("Bearer")) {
            SearchNearbyModel model = new SearchNearbyModel();
            model.setRequestId(Integer.valueOf(parameters.getQueryParameters().getFirst("requestId")));
            model.setTransactionSource(parameters.getQueryParameters().getFirst("transactionSource"));
            model.setLatitude(parameters.getQueryParameters().getFirst("position_lat"));
            model.setLongitude(parameters.getQueryParameters().getFirst("position_lng"));
            model.setAddress(parameters.getQueryParameters().getFirst("address"));
            model.setAmount(Integer.valueOf(parameters.getQueryParameters().getFirst("amount")));

            SearchNearbyController controller = new SearchNearbyController();

            return Response.ok().entity(controller.getSearchNearby(authorization, model)).build();
        }

        return Response.serverError().build();
    }

    @GET
    @Path("/getconfiguration")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConfiguration(@Context HttpHeaders headers, @Context UriInfo parameters) {
        String authorization = headers.getRequestHeader("Authorization").get(0);

        if (authorization.startsWith("Bearer")) {
            ConfigurationModel model = new ConfigurationModel();
            model.setRequestId(Integer.valueOf(parameters.getQueryParameters().getFirst("requestId")));
            model.setPartnerid(parameters.getQueryParameters().getFirst("partnerid"));

            ConfigurationController controller = new ConfigurationController();
            return Response.ok().entity(controller.getConfiguration(authorization, model)).build();

        }

        return Response.serverError().build();
    }
}
