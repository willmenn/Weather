To build the project and run the tests:

```
$ ./gradlew build
```
To run:

```
 java -jar build/libs/weather-0.0.1-SNAPSHOT.jar
```

- The project is structured in rest, service, domain and client layers.

- The project is using Swagger as API doc.

- To execute the endpoint `/data`, execute:
```
$ curl -X GET curl -X GET --header 'Accept: application/json' 'http://localhost:8080/data?city=London%2Cus'

```

- I'm using Pitest to verify the quality of the unit tests.

