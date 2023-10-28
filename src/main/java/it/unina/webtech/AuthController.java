package it.unina.webtech;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import it.unina.webtech.data.User;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Path("auth")
public class AuthController {

    private static final String ISSUER = "todo-rest-api";
    private static final Algorithm algorithm = Algorithm.HMAC256("very_secret_key_not_to_share");
    private static final JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer(ISSUER)
            .build();

    public static String getUsernameClaim(String token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getClaim("username").asString();
        } catch (JWTVerificationException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(User user){

        if (user.getUsername() == null || user.getPassword() == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }

        if (user.getUsername().equals("luigi") && user.getPassword().equals("luigi")) {
            String token = createJWT(user.getUsername(), TimeUnit.DAYS.toMillis(365)); //a little bit too long..

            return Response
                    .status(Response.Status.OK)
                    .entity(token)
                    .build();
        } else {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .build();
        }


    }

    private String createJWT(String username, long ttlMillis) {

        String token = JWT.create()
                .withIssuer(ISSUER)
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + ttlMillis))
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);

        return token;
    }

    public static boolean validateToken(String token){
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}