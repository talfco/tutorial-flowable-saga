package net.cloudburo.camel.saga1;

import net.cloudburo.app.EventMessage;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Saga1ProcessRoutes extends RouteBuilder {

    private Logger logger = LoggerFactory.getLogger(Saga1ProcessRoutes.class);

    @Override
    public void configure()  {
        // Flow triggered from Flowable Task
        from("flowable:saga1Process:camelAsyncTask")
                .to("seda:continueAsync")
                .log(LoggingLevel.INFO, logger, "Internal Flowable Camel Route: ${propertyInput}")
                .transform().simple("Processed: ${property.input}, { Result: OK }");
                //.to("flowable:saga1Process:receiveAsyncTask");
        // Flow triggered from a rabbit mq delivery
        from("rabbitmq:spring-boot-exchange1?exchangeType=topic&routingKey=cloudburo.demo")
                .log(LoggingLevel.INFO, logger, "Rabbit MQ Message Received: ${body}")
                .unmarshal().json(JsonLibrary.Jackson, EventMessage.class)
                .log(LoggingLevel.INFO, logger, "Unmarshalled Response: ${body.id}")
                .process(exchange -> {
                      EventMessage msg = exchange.getIn().getBody(EventMessage.class);
                      exchange.getIn().setBody(msg.getMessage());
                      exchange.setProperty("PROCESS_ID_PROPERTY",msg.getId());
                })
                .to("flowable:saga1Process:receiveAsyncTask");
    }
}
