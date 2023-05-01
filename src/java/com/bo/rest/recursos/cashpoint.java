
package com.bo.rest.recursos;

import com.bo.rest.modelos.Token;
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
    @GET
    @Path("/saludo")
    @Produces(MediaType.TEXT_PLAIN)
    public String saludo(){
        return "hola, metodo get inicial";
    }
     @GET
    @Path("/saludo2")
    @Produces(MediaType.TEXT_PLAIN)
    public String saludo2(){
        return "hola, metodo 2 de get";
    }
    
    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getToken(@PathParam("source") String source, Object body) {

        Token token = new Token("", "", "");

        if (source == "partner") {
            token.setUniqueCustomerIdentifier("");
            token.setEmail("");
            return Response.ok().entity("partner").build();
        } else if (source == "cashpoint") {
            token.setExternalDeviceId("");
            return Response.ok().entity("cashpoint").build();
        }

        return Response.ok().entity("").build();
    }
    
}
