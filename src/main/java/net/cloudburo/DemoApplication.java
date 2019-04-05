package net.cloudburo;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

//@ComponentScan(basePackages = "net.cloudburo")
@SpringBootApplication
public class DemoApplication {

    Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(final RepositoryService repositoryService,
                                  final RuntimeService runtimeService,
                                  final TaskService taskService) {

        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
                logger.info("Number of process definitions : " + query.count());
                List<ProcessDefinition> definitions = query.list();
                for (ProcessDefinition definition : definitions) {
                    logger.info("Loaded Process Definitions: "+definition.getKey());
                }

                // Execute the Intro Process
                runtimeService.startProcessInstanceByKey("introProcess");
                //System.out.println("Number of tasks after process start: "
                //        + taskService.createTaskQuery().count());

                // Execute the Saga1 Process
                runtimeService.startProcessInstanceByKey("saga1Process");
                //System.out.println("Number of tasks after process start: "
                //        + taskService.createTaskQuery().count());
            }
        };
    }
}
