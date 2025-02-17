# Developer Assessment

## Pre-requisite and versions

- Java 21
- Maven 3.9.9
- Spring boot 3.3.8

## Commands

- To run test

```shell
./mvnw clean test
```

- To run compile

```shell
./mvnw clean compile
```

- To start server, application with run in `8081`

```shell
./mvnw clean spring-boot:run
```

- Once application is running you can find the OpenAPI specs
  at [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)
  and [http://localhost:8081/v3/api-docs](http://localhost:8081/v3/api-docs)
- If you are using Intellij, sample HTTP API files can be found here `http/api.http` in this repo
- Used Nager API Open API to make API calls.

## Description

Write a program that retrieves the following information given the data from the API as described
on https://date.nager.at/Api:

- Given a country, return the last celebrated 3 holidays (date and name). - DONE
- Given a year and country codes, for each country return a number of public holidays not falling on weekends (sort in
  descending order). - DONE
- Given a year and 2 country codes, return the deduplicated list of dates celebrated in both countries (date + local
  names) - DONE

## Requirements

- The program should be written in Java or any other language targeting the JVM.
- Write production-ready code.
- Strive for effective implementation considering execution time, memory and algorithmic complexity.
- Document how to run the application.
- Publish the source code into GitHub (using your own personal account) and share it with us.

You will be asked during the interview to demo and explain your code via a screensharing session in a Teams meeting.
