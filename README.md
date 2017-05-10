# Java Micro Frameworks Workshop for Fontys

This small Spring Boot application can be used as inspiration for the apps you'll build during the Java micro frameworks workshop for Fontys.
We intentionally used a different framework for this app,
to try to prevent steering your implementation in a certain direction as much as possible.
This app contains some database interaction and uses an encryption library that might be useful as a basis for your app.

## Prerequisites

To be able to build and run this app, you need the following.

1. [Git](https://git-scm.com/)
1. [Java SE Development Kit for Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
1. [Maven](http://maven.apache.org/)
1. [SQLite](https://sqlite.org/)

## Building and running the app

1. Check out this repository.
1. Navigate to the folder of the repository.
1. Execute `mvn clean verify` to run the tests and build the jar.
1. Execute `java -jar target/micro-0.0.1-SNAPSHOT.jar` to run the app.

## Using the app

With your app running at http://localhost:8080/, you can create an account by posting the following request
(using [Postman](https://www.getpostman.com/), for example).

```
POST http://localhost:8080/account

{
  "username": "John Doe",
  "password": "test123"
}
```

This will result in a response of the following form.

```
{
  "id": 1,
  "username": "John Doe"
}
```

Given a valid account identifier, you can update an account as follows.

```
PUT http://localhost:8080/account/1
{
  "username": "Jane Doe",
  "password": "test1234",
  "currentPassword": "test123"
}
```

This will result in a response similar to the one shown above.
