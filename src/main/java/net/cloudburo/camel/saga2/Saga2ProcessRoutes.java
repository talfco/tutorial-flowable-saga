package net.cloudburo.camel.saga2;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// References
// https://stackoverflow.com/questions/26838086/how-to-implement-the-retry-on-timeout-in-camel

@Component
public class Saga2ProcessRoutes extends RouteBuilder {

    Logger logger = LoggerFactory.getLogger(Saga2ProcessRoutes.class);

    @Override
    public void configure()  {
        // ExceptionHandler with RedliveryPolicy
        errorHandler(defaultErrorHandler()
                .maximumRedeliveries(3)
                .backOffMultiplier(4)
                .retryAttemptedLogLevel(LoggingLevel.WARN));

    }

}
