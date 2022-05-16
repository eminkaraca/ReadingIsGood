# Reading Is Good
is an online books retail firm which operates only on the Internet. Main
target of ReadingIsGood is to deliver books from its one centralized warehouse to their
customers within the same day. That is why stock consistency is the first priority for their
vision operations.

## Technologies and Prerequisites

>Spring Boot, Spring Web
> JWT for security
> MongoDB for database
> JaVers for logging changes on entities
> Docker for publish
> maven for build
> Swagger 2 and javadocs for documentation


## How to build and start server

Command to build with maven:
> mvn clean install

Command to package application
> mvn package

To build Docker: go to project folder from command prompt
> docker build -t emin-karaca-getir-assignment:latest .

To download latest mongoDB
>docker pull mongo

TO run MongoDb
>docker run -d --name mongo-on-docker -p 27017:27017 mongo

To run the Docker
> docker run -p 8081:8080 emin-karaca-getir-assignment

Application will be running on
> > localhost:8081

Swagger URL
> http://localhost:8081/swagger-ui.html#/

Documentation
>./Documentation/index.html

>* If you do not want to use Docker just skip docker comments but first change MongodbUrl from application.properties
>use **spring.data.mongodb.uri=mongodb://localhost:27017/ReadingIsGood** instead of spring.data.mongodb.host=host.docker.internal

## Controllers
>* BookController (Persist new book, Update book's stock)
>* CustomerController (Login, Persist new customers, Query all orders of the customer by paging)
>* OrderController (Persist new order, Query order by id, List orders by date interval)
>* StaticsController (Serve customer’s monthly order statistics) 

##Authentication
JWT uset for Authentication, **CustomerPersist and CustomerLogin** does not require any authentication
## How to use
-Import PostMan Collection by using **ReadingIsGood.postman_collection.json** inside the project file
- Create a new Customer by using **CustomerPersist** request from Postman 
- Login by **CustomerLogin** it will generate a token, use that token in the header of other requests
- Change the value of Authorization header item in the postman requests

##Responses
All responses generated from RigException class which has an HttpStatus code and description

###Successful Responses
> HttpStatus.CREATED        -> for successfully create objects
> HttpStatus.OK             -> for successful operations

###UnSuccessful Responses
> HttpStatus.BAD_REQUEST                -> if parameters are wrong
> HttpStatus.NO_CONTENT                 -> if searched object is not exist
> HttpStatus.INTERNAL_SERVER_ERROR      -> for internal errors
> HttpStatus.NOT_ACCEPTABLE             -> if parameters are invalid
> HttpStatus.NOT_FOUND                  -> if required object is not exist