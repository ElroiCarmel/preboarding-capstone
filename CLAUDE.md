## Description 
RESTful API for managing tasks

## Tech Stack
- Java 17
- Spring Boot 4.1.2

## Commands
- build with `./mvnw clean package`
- test with `./mvnw test`

## Conventions
- Controllers only handle HTTP requests; logic goes into service class

## Rules
- Every new endpoint needs a WebMvcTest and RestTestClient test
- Run the tests before saying you're done
- Test response status code
