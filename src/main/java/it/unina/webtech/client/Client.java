package it.unina.webtech.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {

    private static final String BASE_URI = "http://localhost:9000/";

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest loginRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URI + "auth"))
                .setHeader("Content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"username\":\"luigi\", \"password\":\"luigi\"}"))
                .build();
        HttpResponse<String> loginResponse = client.send(loginRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(loginResponse.toString());

        System.out.println("\nLogin Response");
        System.out.println("Status code: " + loginResponse.statusCode());
        System.out.println("Body       : " + loginResponse.body());

        String token = loginResponse.body();

        // prepare request to get all todos
        HttpRequest listTodoRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URI + "todo"))
                .headers("Authorization","Bearer " + token) //set authorization header accordingly
                .GET().build();
        HttpResponse<String> listTodoResponse = client.send(listTodoRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println("\nList To-do Response");
        System.out.println("Status code: " + listTodoResponse.statusCode());
        System.out.println("Body       : " + listTodoResponse.body());

        //add a new To-do
        HttpRequest addTodoRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URI + "todo"))
                .headers("Content-type", "application/json")
                .headers("Authorization","Bearer " + token) //set authorization header accordingly
                .POST(HttpRequest.BodyPublishers.ofString("{\"id\":1, \"title\":\"Pass Software Engineering\", \"description\":\"Do project, Study\"}"))
                .build();
        HttpResponse<String> addTodoResponse = client.send(addTodoRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(addTodoResponse.toString());

        System.out.println("\nAdd To-do Response");
        System.out.println("Status code: " + addTodoResponse.statusCode());
        System.out.println("Body       : " + addTodoResponse.body());

        // prepare request to get all todos
         listTodoRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URI + "todo"))
                .headers("Authorization","Bearer " + token) //set authorization header accordingly
                .GET().build();
         listTodoResponse = client.send(listTodoRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println("\nList To-do Response");
        System.out.println("Status code: " + listTodoResponse.statusCode());
        System.out.println("Body       : " + listTodoResponse.body());

    }
}
