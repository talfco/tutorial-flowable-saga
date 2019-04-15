package net.cloudburo;

import java.util.List;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * The AMQP Message Sender using the Spring Rabbit Template
 */
@Component
public class DemoAppRunner implements CommandLineRunner {

    static final String topicExchangeName = "spring-boot-exchange1";

    Logger logger = LoggerFactory.getLogger(DemoAppRunner.class);

    private final RabbitTemplate rabbitTemplate;
    private final RepositoryService repositoryService;
    private final RuntimeService runtimeService;
    private final TaskService taskService;

    public DemoAppRunner(RepositoryService repositoryService, RuntimeService runtimeService, TaskService taskService, RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.repositoryService = repositoryService;
        this.runtimeService = runtimeService;
        this.taskService = taskService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Flowable Process Definition
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        logger.info("Number of process definitions : " + query.count());
        List<ProcessDefinition> definitions = query.list();
        for (ProcessDefinition definition : definitions) {
            logger.info("Loaded Process Definitions: "+definition.getKey());
        }

        // Execute the Saga1 Process
        ProcessInstance pr = runtimeService.startProcessInstanceByKey("saga1Process");
        logger.debug("Got Processing Instance Id "+pr.getId());
        logger.info("Number of tasks after process start: "
                + taskService.createTaskQuery().count());

        // Send a message to a flowable task
        String json  = "{  \"id\" :  \""+pr.getId()+"\", \"message\": \"Hello from RabbitMQ\"}";
        logger.debug("Sending message: "+json);
        rabbitTemplate.convertAndSend(topicExchangeName, "cloudburo.demo", json);
    }

}