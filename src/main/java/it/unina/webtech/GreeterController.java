package it.unina.webtech;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;

import java.util.List;

@Path("greet")
public class GreeterController {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String greet(@Context HttpHeaders headers){
        String token = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        if(token == null || !token.startsWith("Bearer"))
            return "Hello Unauthenticated User!";
        else {
            token = token.substring("Bearer".length()).trim();
            if( AuthController.validateToken(token) ) {
                String username = AuthController.getUsernameClaim(token);
                return "Hello " + username;
            } else {
                return "Hello User! Your authentication is invalid!";
            }
        }
    }

}
