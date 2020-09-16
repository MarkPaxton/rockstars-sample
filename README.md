# Rockstars Sample Application

This is created using java, maven, spring boot, docker and mongodb.

# Build the project

```sh
$ mvn compile
```

# Test the project

To run the integration tests for the Mongo database connection, rebuild and start the test server container in a terminal window:
```sh
$ cd mongo-db-test
$ ./start-test-db.sh
```
```sh
$ mvn test
```
Bounce the database container (CTRL-C, run again) to reset the test data between test runs


# Run the project standalone

Run the project standalone 

_Start the test database container running as above - skip the tests if desired
```sh
$ mvn install -DskipTests
$ cd app
$ mvn org.springframework.boot:spring-boot-maven-plugin:run -X -Dspring-boot.run.profiles=standalone
```

# Run the project in docker

Run the project using docker-compose
```sh
$ mvn package 
$ docker-compose build
$ docker-compose up
```

The service is now up on port 8080.

# Sample requests

Get artists
localhost:8080/rockstars/artists

Get artist by name
localhost:8080/rockstars/artist/name/{name}

Get songs
localhost:8080/rockstars/song

Get songs by genre
localhost:8080/rockstars/songs/{genre}

Get songs before a year
localhost:8080/rockstars/songs/before/{year}

Examples:
```
localhost:8080/rockstars/songs/Metal
localhost:8080/rockstars/songs/before/2016


