package org.accolite.kafka;

import org.accolite.model.User;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class UserRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // URI specifying topic and broker
        String kafkaUri = "kafka:test-topic?brokers=localhost:9092";
        // User class project path
        String userPath = User.class.getName();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());


        restConfiguration().bindingMode(RestBindingMode.json);

        rest("api/kafka/user")
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

        // handling all users request
        from("direct:getUsers")
                .to("jpa://"+ userPath+"?resultClass="+userPath+"&namedQuery=findAll")
                .log("Users List: ");

        // handling single user request by ID
        from("direct:getUser")
                .toD("jpa://"+userPath+"?query=SELECT u FROM " + userPath +" u WHERE u.id= ${header.id}",5)
                .log("some ${header.request}")
                .choice()
                    .when().simple("${body} == '[]'")
                        .setBody(simple(String.format("{'event':'GET', 'status':'%s', 'message':'User not found', " +
                                "'user':{'id': ${header.id}}, 'timestamp': %s }",
                                "FAIL", timestamp)))
                        .to(kafkaUri)
                    .otherwise()
                        .setBody(simple(String.format("{'event':'GET', 'status':'%s', 'message':'User found', " +
                                "'user':{'id': ${body[0].id}, 'name' : '${body[0].name}'}, 'timestamp': %s }",
                                "SUCCESS", timestamp)))
                        .to(kafkaUri);

        // handling user creation
        from("direct:addUser")
                .to("jpa://"+userPath+"?usePersist=true")
                .setBody(simple(String.format("{'event':'POST', 'status':'%s', 'message':'User added', " +
                                "'user':{'id': ${body.id}, 'name' : '${body.name}'}, 'timestamp': %s }",
                                "SUCCESS", timestamp)))
                .to(kafkaUri);

        // removing existing user
        from("direct:removeUser")
                .toD("jpa://"+userPath+"?nativeQuery=DELETE FROM users WHERE id = ${header.id}&useExecuteUpdate=true")
                .setBody(simple(String.format("{'event':'DELETE', 'status':'%s', 'message':'User deleted', " +
                                "'user':{'id': ${header.id}}, 'timestamp': %s }",
                                "SUCCESS", timestamp)))
                .to(kafkaUri);

        // updating existing user
        from("direct:updateUser")
                .toD("jpa://"+userPath+"?nativeQuery=UPDATE users SET name = '${body.name}' WHERE id = ${header.id}&useExecuteUpdate=true")
                .choice()
                .when().simple("${body} == 0")
                    .setBody(simple(String.format("{'event':'PUT', 'status':'%s', 'message':'User not found', " +
                                    "'user':{'id': ${header.id}}, 'timestamp': %s }",
                                    "FAIL", timestamp)))
                    .to(kafkaUri)
                .otherwise()
                    .setBody(simple(String.format("{'event':'PUT', 'status':'%s', 'message':'User updated', " +
                                    "'user':{'id': ${header.id}}, 'timestamp': %s }",
                                    "SUCCESS", timestamp)))
                    .to(kafkaUri);

    }

}