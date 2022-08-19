# Microservices accelerator

## How to run in local:
### Required
- Install docker
- Install maven
- Install kafka
- Install `curl` cli(MacOS:https://formulae.brew.sh/formula/curl)
```bash
brew install curl
```
- Install `make` cli(MacOS:https://formulae.brew.sh/formula/make)
```bash
brew install make
```

### Run steps

[comment]: <> (Step1: Use `make` to execute makefile run mysql in docker)

[comment]: <> (```bash)

[comment]: <> (make docker-up)

[comment]: <> (```)

[comment]: <> (OR  use docker-compose command directly)

[comment]: <> (```bash)

[comment]: <> (docker-compose -f docker-compose.yaml up --build)

[comment]: <> (```)

Step1: Open project in backend folder layer

Step2: Use `make` to execute makefile run application
```bash
make run
```
OR  use mvnw command directly
```bash
./mvnw compile quarkus:dev
```

Step3: Use `curl` to call API
```bash
curl -X POST http://localhost:8080/api/v1/ms-accelerator/generate \
-H "Content-Type: application/json" \
-d '{
  "language":"java",
  "framework":"quarkus",
  "groupId" : "org.accolite",
  "artifactId": "getting-started",
  "build": "maven",
  "deploy": "docker",
  "orchestration": "kubernetes",
  "security":"quarkus-security",
  "authentication": "oauth",
  "tracing": "opentracing",
  "monitoring": "grafana",
  "logging": "slf4j",
  "entities":[{"entity_name":"User","fields":[["  String","name"],["String","address"],["long","phoneNumber"]]},{"entity_name":"Url","fields":[["String","long_url"],["String","short_url"]]}],
  "database":"mysql"
}'
```
Swagger: http://localhost:8080/swagger-ui

### Packaging and running the application

The application can be packaged using:
```shell script
./mvnw clean package
```