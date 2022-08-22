package org.accolite.kafka;


import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ConsumerRoute extends RouteBuilder {

    public static final String KAFKA_ROUTE_NAME = "kafka-consumer";

    @Override
    public void configure() throws Exception {
        from("kafka:test-topic?brokers=localhost:9092").routeId(KAFKA_ROUTE_NAME)
                .log("Message: ${body}  received on the topic: ${headers[kafka.TOPIC]} ");

    }
}



