# How to run

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

## Default api
Swagger: http://localhost:8080/

### Create user api
```shell script
curl http://localhost:8080/api/user \
-d '{ "name" : "boris"}'
```

### Get user name api
```shell script
curl http://localhost:8080/api/user-service/{id}
```
example:
```shell script
curl http://localhost:8080/api/user-service/1
```