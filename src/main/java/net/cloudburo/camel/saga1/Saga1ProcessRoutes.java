package net.cloudburo.camel.saga1;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Saga1ProcessRoutes extends RouteBuilder {

    private Logger logger = LoggerFactory.getLogger(Saga1ProcessRoutes.class);

    @Override
    public void configure()  {

        from("flowable:saga1Process:camelAsyncTask")
                .to("seda:continueAsync")
                .log(LoggingLevel.INFO, logger, "External System Processing...")
                .transform().simple("Processed: ${property.input}, { Result: OK }");

        from("seda:continueAsync")
                .to("flowable:saga1Process:receiveAsyncTask");
    }
}
