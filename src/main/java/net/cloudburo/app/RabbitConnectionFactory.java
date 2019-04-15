package net.cloudburo.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component("rabbitConnFactory")
public class RabbitConnectionFactory extends com.rabbitmq.client.ConnectionFactory {

    private Logger logger = LoggerFactory.getLogger(RabbitConnectionFactory.class);

    public RabbitConnectionFactory(Environment env) {
        super();
        setUsername(env.getProperty("spring.rabbitmq.username"));
        setPassword(env.getProperty("spring.rabbitmq.password"));
        setHost(env.getProperty("spring.rabbitmq.host"));
        setPort(Integer.parseInt(env.getProperty("spring.rabbitmq.port")));
        logger.debug("RabbitConnectionFactory initialized with: "+getHost()+":"+getPort());
    }
}
