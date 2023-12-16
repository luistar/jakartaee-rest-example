package it.unina.webtech;

import it.unina.webtech.dao.TodoItemDAO;
import it.unina.webtech.data.TodoItem;
import it.unina.webtech.filter.RequireJWTAuthentication;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

/**
 * Todo resource (exposed at "todos" path)
 */
@Path("/todos")
public class TodoController {

    @GET
    @RequireJWTAuthentication
    @Produces(MediaType.APPLICATION_JSON)
    public List<TodoItem> getAll() {
        return TodoItemDAO.getAll();
    }

    @POST
    @RequireJWTAuthentication
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTodoItem(TodoItem todo){
        System.out.println("Adding todo: " + todo.toString());
        try {
            TodoItemDAO.add(todo);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @RequireJWTAuthentication
    @Path("{id}")
    public Response deleteTodoItem(@PathParam("id") int id){
        System.out.println("Delete request for id " + id);
        try {
            TodoItemDAO.deleteById(id);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

}
