# How to run

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

## Default api
Swagger: http://localhost:8080/swagger-ui


### Health check api
```shell script
curl -X GET "http://localhost:8080/api/health" 
```

### Create user api
example:
```shell script
curl -X POST "http://localhost:8080/api/user-service/user" \
-H "Content-Type:application/json" \
-d '{ "name":"Boris" }'
```

### Get user name api
```shell script
curl -X GET http://localhost:8080/api/user-service/{id}
```
example:
```shell script
curl -X GET http://localhost:8080/api/user-service/1
```

### Update user api
```shell script
curl -X PUT "http://localhost:8080/api/user-service/{id}" \
-H "Content-Type:application/json" \
-d '{ "name":"Boris" }'
```

example:
```shell script
curl -X PUT "http://localhost:8080/api/user-service/1" \
-H "Content-Type:application/json" \
-d '{ "name":"Harry" }'
```

### Delete user name api
```shell script
curl -X DELETE http://localhost:8080/api/user-service/{id}
```
example:
```shell script
curl -X DELETE http://localhost:8080/api/user-service/1
```

