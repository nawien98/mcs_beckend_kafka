# Kafka progress report

Following to what have been discussed, it was set up a plan of deliverables to be showcased by the end of the week for two weeks.

1.  Week one:
    - Set up Apache Kafka - with test topic
    - Be able to perform health checks
    - Configure Quarkus plugin for Kafka
2.  Week two:
    - Send and consume messages to/from test topic
    - Be able to check if the message is received or placed
    - Demo

## Week one

### Set up Apache Kafka - with test topic

First step we have to download and extract the latest [Kafka release](https://www.apache.org/dyn/closer.cgi?path=/kafka/3.2.1/kafka_2.13-3.2.1.tgz).
Using the terminal we will moving into the extracted Kafka files.
Then we have to run the required **Zookeeper**, in order to do that we will have to run the following command:

    $ bin/zookeeper-server-start.sh config/zookeeper.properties

Once the Zookeeper server is up and running it is now possible to run the **Kafka** environment by using this command on a new terminal:

    $ bin/kafka-server-start.sh config/server.properties

We could now start using our set up Apache Kafka by trying to create a topic we can do that by executing this command:

    $ bin/kafka-topics.sh --create --topic test-topic --bootstrap-server localhost:9092

Now that our environment is been successfully set up and we have created our topic are ready to perform some essential health checks.

### Be able to perform health checks

We have different ways to perform health checks on our environment one of the most notorious way to monitor is by using Grafana that will allow us to live showcase and display the majority of the information starting from the topics followed by the different partitions up to the point that we are able to visualise the logs of the different consumers. Those type of information are gathered and displayed on those graphs by linking different data.
By using our terminal we can visualise some basic information, for example we could visualise that our Kafka environment will throw errors if the Zookeeper Server is down.
One way that we could check if the Kafka environment is running will be by trying to create a topic that will show that the broker my not be available.
To understand if the topic we are creating has been added successfully we can check that by running this command:

    $ bin/kafka-topics.sh --list --bootstrap-server localhost:9092

We currently haven’t created a complex topic with different partition but if we want to have specific information on the topic we did create we can run:

    $ bin/kafka-topics.sh --describe --topic test-topic --bootstrap-server localhost:9092

One final check that we can bring on is the consumer/producer test. On one terminal we can have a console with the
consumer up running (read):

    $ bin/kafka-console-consumer.sh --topic test-topic --from-beginning --bootstrap-server localhost:9092

and the producer (write) in the other terminal:

    $ bin/kafka-console-producer.sh --topic test-topic --bootstrap-server localhost:9092

by writing in this our last terminal we should be able to see on the consumer console the latest messages.

More relevant, to our project once we implement Kafka on Quarkus with the dependencies and the corresponding routes, Apache Kafka does become and essential environment for the project to start with therefore we will have on our log displayed if the connection hasn’t taken place. As previously we have been handling the log events and we have changed its level to: TRACE.

### Configure Quarkus plugin for Kafka

To configure it with the existing Microservices project there were few basic steps we had to follow, as we are working in local with the predefined ports we didn’t had the necessity to specify any ports on the properties. In order to define the dependencies we did alter the pom.xml file by adding :

     <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>io.quarkus.platform</groupId>
                <artifactId>quarkus-bom</artifactId>
                <version>${quarkus.platform.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.apache.camel.quarkus</groupId>
                <artifactId>camel-quarkus-bom</artifactId>
                <version>${camel-quarkus.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>org.apache.camel.quarkus</groupId>
            <artifactId>camel-quarkus-bean</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.camel.quarkus</groupId>
            <artifactId>camel-quarkus-direct</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.camel.quarkus</groupId>
            <artifactId>camel-quarkus-jackson</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.camel.quarkus</groupId>
            <artifactId>camel-quarkus-rest</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.camel.quarkus</groupId>
            <artifactId>camel-quarkus-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-smallrye-reactive-messaging-kafka</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.camel.quarkus</groupId>
            <artifactId>camel-quarkus-kafka</artifactId>
        </dependency>
    </dependencies>

The initial dependencies we have are Apache Camel oriented and those will help us in defining in the following section the routes the response/request and make the to/from approach possible.

The second last dependency help us perform a more accurate health check than our manual testing we have provided previously, to accomplish that goal we had to alter our application.property file and setting to **true** `quarkus.kafka.health.enabled`, the command to be used on our terminal is:

```Bash
    $ curl localhost:8080/q/health
```

By adding the last dependency we are able to perform as a Producer to reports the event as messages on the predefined topic which in our case we decided to continue by using topic-test.

## Week two

### Send and consume messages to/from test topic

Followed by the setting up we are now able to create on the project the producer that will be sending the request flows of the User to Apache Kafka. To make it inherent to our project it was established that we will try to write for the four CRUD operations: create/get/update/delete user.
We should start by describing the Apache approach that will be used to define the routes and to manage the request. [Code](https://github.com/nawien98/mcs_beckend_kafka/blob/main/backend/src/main/java/org/accolite/kafka/UserRoute.java) available on git.
We will start by defining the class UserRoute and applying routes and linking them to where they should be redirected:

```Bash
    rest("/user")
        .get("/all")
            .to("direct:getUsers")
        .get("/{id}")
            .to("direct:getUser")
        .post()
            .type(User.class)
            .to("direct:addUser")
        .delete("/{id}")
            .to("direct:removeUser")
        .put("/{id}")
            .type(User.class)
            .to("direct:updateUser");
```

At this point we have the inner class handling of those redirecting we will start with the most simple which is the creation followed by the deletion:

```Bash
    // creation
    from("direct:addUser")
        .to("jpa://"+User.class.getName()+"?usePersist=true")
        .setBody(simple("User ${body.id}:${body.name} added successfully"))
        .to("kafka:test-topic?brokers=localhost:9092");

    // delete
    from("direct:removeUser")
        .toD("jpa://"+User.class.getName()+"?nativeQuery=DELETE FROM users WHERE id = ${header.id}&useExecuteUpdate=true")
        .setBody(simple("Deleted user with id: ${header.id}"))
        .to("kafka:test-topic?brokers=localhost:9092");
```

We are using JPA (which might need the [dependency](https://camel.apache.org/camel-quarkus/2.11.x/reference/extensions/jpa.html) if not yet imported) to communicate with our SQL server and we are then creating the body of the message that we are sanding to KAFKA in which we do define the topic and the broker at the end.

We are now left with the GET and PUT operations and we would like to handle some possible errors which will be a wrong id for example.

```Bash
    // GET request
    from("direct:getUser")
        .toD("jpa://"+User.class.getName()+"?query=SELECT u FROM " + userPath +" u WHERE u.id= ${header.id}",5)
        .choice()
            .when().simple("${body} == '[]'")
                .setBody(simple("User with id: ${header.id} not found"))
                .to("kafka:test-topic?brokers=localhost:9092")
            .otherwise()
                .setBody(simple("Found User ${header.id}:${body[0].name}"))
                .to("kafka:test-topic?brokers=localhost:9092");

    // UPDATE request
    from("direct:updateUser")
         .toD("jpa://"+User.class.getName()+"?nativeQuery=UPDATE users SET name = '${body.name}' WHERE id = ${header.id}&useExecuteUpdate=true")
         .choice()
            .when().simple("${body} == 0")
                .setBody(simple("No such user with id: ${header.id}"))
                .to("kafka:test-topic?brokers=localhost:9092")
            .otherwise()
                .setBody(simple("Updated user with id: ${body}"))
                .to("kafka:test-topic?brokers=localhost:9092");
```

To test this new implementations we have to make sure that Kafka environment is up and running, we could use Postman to make our request as well as curl command line request and visualise the output on our terminal. By the end of this steps we should be able to establish a connection with Kafka and to also set up the producer that delivers the desired message to a topic of our choice.
Next we would like to make sure that those message have been placed, to do so we will write the code for the consumer.

### Be able to check if the message is received or placed

To facilitate ourselves we will be using the same project to create our Consumer, a common pattern is to have multiple consumers fetching from different partitions. Kafka's goal is to decentralise this type of events and to monitor the realtime pipeline built as well storing both historical data. In our case we will be delivering under the same project by using this [code](https://github.com/nawien98/mcs_beckend_kafka/blob/main/backend/src/main/java/org/accolite/kafka/ConsumerRoute.java):

```Bash
    @Override
    public void configure() throws Exception {
        from("kafka:test-topic?brokers=localhost:9092").routeId(KAFKA_ROUTE_NAME)
                .log("Message: ${body}  received on the topic: ${headers[kafka.TOPIC]} ");
    }
```

We could think about this code as a listener as whenever a message hits the topic we are simply pulling out of it the latest message and displaying on our project logs. This allow us to understand whether a producer has made any sorts of request from anywhere, and to store them. In a more complex usage we could think about monetary transactions and the realtime pipeline we could create the the request queuing and to store the request flow. The use of several partitions on different brokers makes the distributed environment possible and provides us with a flawless experience.

### Demo

We have come to a certain end and I will be using this section more like a deployment guideline document so that could be used to reference and start up the [project](https://github.com/nawien98/mcs_beckend_kafka/tree/main/backend).
The project has been pushed into a repository and the altered sections could be found in the backend folder.

#### How to run in local

##### Required:

- Install Docker
- Install Maven
- Install Kafka
- Install `curl` cli(MacOS:https://formulae.brew.sh/formula/curl)

```bash
brew install curl
```

- Install `make` cli(MacOS:https://formulae.brew.sh/formula/make)

```bash
brew install make
```

##### Run steps

Step1: Run Zookeper server

Step2: Run Kafka environment

Step3: Use `make` to execute makefile run application

```bash
make run
```

OR use mvnw command directly

```
./mvnw compile quarkus:dev
```

Step4: Use `curl` to call API

CREATE

```bash
curl -X POST http://localhost:8080/api/kafka/user \
-H "Content-Type: application/json" \
-d '{
   "name":"Kaf"
	}'
```

GET

```bash
curl -X GET http://localhost:8080/api/kafka/user/1
```

UPDATE

```bash
curl -X PUT http://localhost:8080/api/kafka/user/1 \
-H "Content-Type: application/json" \
-d '{
    "name":"Kafka"
    }'
```

DELETE

```bash
curl -X DELETE http://localhost:8080/api/kafka/user/1
```
