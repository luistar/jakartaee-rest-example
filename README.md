# REST API Example

This repository contains a simple starter example for implementing a REST API using [Jakarta EE](https://jakarta.ee/specifications/restful-ws/).
The example features authentication using JWT, via the [java-jwt](https://github.com/auth0/java-jwt) library by Auth0.
The example also includes a built-in [Grizzly](https://projects.eclipse.org/projects/ee4j.grizzly) server to serve the REST API,
and a Dockerfile to run the REST API in a Docker container.
Last but not least, a few examples of client code using the developed API are available (see the end of the README).


## REST API Endpoints
The example includes three endpoints.

### /auth endpoint
POST requests with a JSON containing a username and a password can be sent to this endpoint.
If the provided user credentials are valid, a JWT is issued and returned in the response.

### /greet endpoint
This endpoint supports GET requests. If the requests contain no Authorization 
header, a default message is returned as a response.
If the requests contains a valid JWT, a custom message showing the username 
associated with the JWT is returned.

### /todo endpoint
This endpoint can be used to manage a To-do list. It supports the following methods:
* `GET /todo`: returns a list of the stored To-do items;
* `POST /todo`: Expects a JSON with the id, title and description properties in the request body, and saves the new To-do item 
in the list. 
* `DELETE /todo/{id}`: Deletes the To-do Item with the given `{id}`.

## Running the REST API locally (requires Java 17+)
To run the REST API locally, build the project using Maven and run the `Main` class 
from your IDE.
By default, the REST API will be available at [http://localhost:8080/](http://localhost:8080/).

## Running the REST API with Docker
To build a docker image, run 
```
docker build -t "todo-rest-api-image" .
```
To run the image, run
```
docker run -p 9000:8080 -it --name todo-rest-api-container todo-rest-api-image
```
The REST API will be available at [http://localhost:9000/](http://localhost:9000/).

## Client
This repository includes also examples on how to write client code that uses the REST API. 
Some tests (see `src/test/java/it/unina/webtech/` show how to write client code using the Jakarta EE Client classes. 
The example in `/src/main/java/client/Client.java`, on the other hand, uses the 
HttpClient included in the Java Standard library since Java 11.

# Disclaimer
The Java code provided in this repository is intended solely for educational purposes.
It has been created as an illustrative example to support Computer Science students
at the *Università degli Studi di Napoli Federico II* in understanding 
specific programming concepts. 

Readers are encouraged to use this code as a reference, a starting point, or 
a source of inspiration. However, it is imperative to exercise caution and 
critical thinking when applying the concepts demonstrated in this code to 
real-world projects.

This code contains simplifications, errors, or inefficiencies
intentionally introduced to highlight specific educational points. As such,
it should not be considered a definitive or exhaustive example of quality
programming.
Furthermore, coding practices evolve, and what might be suitable 
for educational purposes may not necessarily align with industry standards 
or security best practices.

By accessing and utilizing this code, you acknowledge that it is provided "as is" 
without any warranties, express or implied, including, but not limited to, the 
implied warranties of merchantability and fitness for a particular purpose. In no 
event shall the creators and distributors be liable for any direct, indirect, 
incidental, special, exemplary, or consequential damages arising in any way out 
of the use of this code.