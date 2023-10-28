package it.unina.webtech;

import com.fasterxml.jackson.databind.util.JSONPObject;
import it.unina.webtech.data.User;
import jakarta.json.JsonObject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTest {

    private HttpServer server;
    private WebTarget target;

    @BeforeEach
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);
    }

    @AfterEach
    public void tearDown() throws Exception {
        server.stop();
    }

    /**
     * Test to see that the message a login with the correct credentials succeeds, and receives a JWT
     */
    @Test
    public void testAuthSuccess() {
        Response response = target.path("auth").request().post(Entity.json(new User("luigi", "luigi")));
        System.out.println(response);
        assertEquals(200, response.getStatus(), "Response status should be 200 OK");
        assertFalse(response.readEntity(String.class).isEmpty());
    }

    @Test
    public void testAuthFailure() {
        Response response = target.path("auth").request().post(Entity.json(new User("bob", "bob")));
        System.out.println(response);
        assertEquals(401, response.getStatus(), "Response status should be 401 UNAUTHORIZED");
        assertTrue(response.readEntity(String.class).isEmpty());
    }
}
