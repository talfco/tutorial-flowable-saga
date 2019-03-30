package net.cloudburo.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Saga1ProcessRoutes extends RouteBuilder {

    @Override
    public void configure()  {
        from("flowable:saga1Process:camelAsyncTask")
                .to("seda:continueAsync")
                .transform().simple("Processed: ${property.input}, { Result: OK }");

        from("seda:continueAsync")
                .to("flowable:saga1Process:receiveAsyncTask");
    }
}
