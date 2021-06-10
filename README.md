# Tweet Service

## Introduction
This application is twitter-like service which support the following request :
- Register a user
- User login
- Follow a user
- Unfollow a user
- Tweet post
- Fetch list of people following a user
- Fetch list of tweets of a user (including self tweets and replies by followers)

## Tech
Tweet Service uses a number of open source projects to work properly

- OpenJdk 8
- MapStruct
- Lombok
- Spring Boot 2.5.0
- Maven

## Build Application

Install the dependencies

```sh
mvn clean install

```

## Run Application

Change to directory twitter-application

```sh

cd twitter-application

mvn spring-boot:run

```

**Rest API's Postman collection is in the root directory of the project**
