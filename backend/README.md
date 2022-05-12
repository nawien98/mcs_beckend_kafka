# Microservices accelerator

## How to run in local:
### Required
- Install docker
- Install `curl` cli(https://formulae.brew.sh/formula/curl)
```bash
brew install curl
```
- Install `make` cli(https://formulae.brew.sh/formula/make)
```bash
brew install make
```

### Run steps
Step1: Use `make` to execute makefile run mysql in docker
```bash
make docker-up
```
OR  use docker-compose command directly
```bash
docker-compose -f docker-compose.yaml up --build
```

Step2: Use `make` to execute makefile run application
```bash
make run
```
OR  use mvnw command directly
```bash
./mvnw quarkus:dev
```

Step3: Use `curl` to call API
```bash
curl -X POST -H "Content-Type: application/json" -d '{"groupId" : "org.acme", "artifactId":"getting-started", "extensions":"resteasy-reactive,quarkus-jdbc-mysql,smallrye-openapi,quarkus-resteasy-jackson"}' "http://localhost:8080/api/v1/ms-accelerator/generate"
```
Swagger: http://localhost:8080/swagger-ui

### Packaging and running the application

The application can be packaged using:
```shell script
./mvnw clean package
```