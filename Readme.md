# Spring Boot, MySQL, JPA, Hibernate Rest API proyect

Build Restful CRUD API for the application RetroExchanges  using Spring Boot, Mysql, JPA and Hibernate.

## Requirements

1. Java - 11.0.14.1

2. Maven - 3.8.5

3. Mysql - 8.0.28

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/martincubino/retroexchanges-rest-service.git
```

**2. Create Mysql database**
```bash
create database retroexchanges_app.sql
```

**3. Change mysql username and password as per your installation**

+ open `src/main/resources/application.properties`

+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

**4. Build and run the app using maven**

```bash
mvn package
java -jar target/retroexchanges-rest-service-1.0.0.jar
```

Alternatively, you can run the app without packaging it using -

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8080>.

## Explore Rest APIs

You can test them using postman or any other rest client.

