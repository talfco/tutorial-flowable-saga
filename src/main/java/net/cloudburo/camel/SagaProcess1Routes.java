package net.cloudburo.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SagaProcess1Routes extends RouteBuilder {

    @Override
    public void configure()  {
        from("flowable:saga1Process:camelAsyncTask")
                .transform().simple("${property.input}")
                .to("seda:continueAsync");

        from("seda:continueAsync")
                .to("flowable:saga1Process:receiveAsyncTask");
    }
}
