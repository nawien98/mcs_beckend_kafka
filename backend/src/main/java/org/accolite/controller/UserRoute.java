package org.accolite.controller;

import org.accolite.model.User;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // URI specifying topic and broker
        String kafkaUri = "kafka:test-topic?brokers=localhost:9092";
        String userPath = User.class.getName();

        restConfiguration().bindingMode(RestBindingMode.json);

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

        // handling all users request
        from("direct:getUsers")
                .to("jpa://"+ userPath+"?resultClass="+userPath+"&namedQuery=findAll")
                .log("Users List: ");

        // handling single user request by ID
        from("direct:getUser")
                .toD("jpa://"+userPath+"?query=SELECT u FROM " + userPath +" u WHERE u.id= ${header.id}",5)
                .choice()
                    .when().simple("${body} == '[]'")
                        .setBody(simple("User ${header.id} not found"))
                        .to(kafkaUri)
                    .otherwise()
                        .setBody(simple("Found User ${header.id}:${body[0].name}"))
                        .to(kafkaUri);

        // handling user creation
        from("direct:addUser")
                .to("jpa://"+userPath+"?usePersist=true")
                .setBody(simple("User ${body.id}:${body.name} added successfully"))
                .to(kafkaUri);

        // removing existing user
        from("direct:removeUser")
                .toD("jpa://"+userPath+"?nativeQuery=DELETE FROM users WHERE id = ${header.id}&useExecuteUpdate=true")
                .setBody(simple("Deleted user with id: ${header.id}"))
                .to(kafkaUri);

        // updating existing user
        from("direct:updateUser")
                .toD("jpa://"+userPath+"?nativeQuery=UPDATE users SET name = '${body.name}' WHERE id = ${header.id}&useExecuteUpdate=true")
                .choice()
                .when().simple("${body} == 0")
                    .setBody(simple("No such user with id: ${header.id}"))
                    .to(kafkaUri)
                .otherwise()
                    .setBody(simple("Updated user with id: ${body}"))
                    .to(kafkaUri);

    }

}