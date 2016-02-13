**spring-data-rest-external-resource-mapping**

This example spring-data-rest project illustrates linking a field within an Entity to a REST resource, as opposed to another table.  

Annotations within an @Entity class indicate the intent of linking to a REST resource, and a custom ResourceProcessor is used to add links and resolve external resources within the HATEOAS output

Some simple data is loaded on startup via a "BootstrapData" class.

Usage:

`./gradlew bootRun`

Endpoints:

`curl http://localhost:8080/users`

`curl http://localhost:8080/profiles`

