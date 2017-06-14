# CQRS Demo using Axon Framework

## Introduction
This is a demo application to demonstrate the use of Spring Boot and the Axon Framework.

This application makes of the following design patterns:
- Domain-Driven Design (DDD)
- Command Query Responsibility Segregation (CQRS)
- Event Sourcing

## Building
To create a standard spring boot executable jar
> mvn clean install

## Running
Make sure a default instance of rabbitmq is running.

On a mac:
> brew install rabbitmq
> brew services start rabbitmq

To run the application:
> mvn spring-boot:run

OR

> mvn clean package
> java -jar target/cqrs-demo-0.0.1-SNAPSHOT.jar

Browse to http://localhost:8080/customers

## References
* Axon Framework - http://www.axonframework.org/
* Spring Boot - http://projects.spring.io/spring-boot/
* Spring Framework - http://projects.spring.io/spring-framework/


