# Tutorials spring-hazelcast-demo

Simply demo application to use Hazelcast repository in a Spring boot project.
Using spring profile is possible to run at the same time multiple distributed instances of Hazelcast.
We can run a distributed test to validate syncing replication of elements in the repo.

## Run test
```
mvn test
```

## Testing distributed Hazelcast instances

Run in a separate shell:
```
mvn spring-boot:run -Dspring-boot.run.profiles=hazelfirst

mvn spring-boot:run -Dspring-boot.run.profiles=hazelsecond

mvn spring-boot:run -Dspring-boot.run.profiles=hazelthird
```


Now in a separate shell search for all the cities associated to a specific country for all the hazelcast instances:

```
curl -X GET   -H 'Content-Type: application/json' http://localhost:8080/cities?byCountry=Italy

curl -X GET   -H 'Content-Type: application/json' http://localhost:8081/cities?byCountry=Italy

curl -X GET   -H 'Content-Type: application/json' http://localhost:8082/cities?byCountry=Italy
```

We expected to have in sync all the data responses in both 3 instances.

Now add another element in the city repository using instance hazelfirst:

```
curl -X POST http://localhost:8080/cities  -H 'Content-Type: application/json'  -d '{"name" : "Florence","country" : "Italy","population" : "1340000"}'
```

Querying all the other instances, we expected to retrieve the element pushed to the first instance.

```
curl -X GET   -H 'Content-Type: application/json' http://localhost:8081/cities?byCountry=Italy

curl -X GET   -H 'Content-Type: application/json' http://localhost:8082/cities?byCountry=Italy
```

## Other query examples 
```
http://localhost:8080/cities?like=%om%
```