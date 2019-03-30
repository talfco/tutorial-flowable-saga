package net.cloudburo;

import org.flowable.engine.*;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testIntroProcess() {
        // Create Flowable engine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // Get main service interfaces
        RepositoryService repositoryService = processEngine.getRepositoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        HistoryService historyService = processEngine.getHistoryService();

        // Deploy intro process definition
        repositoryService.createDeployment().name("introProcessDefinition")
                .addClasspathResource("processes/intro-process.bpmn20.xml")
                .deploy();

        // Start intro process instance
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("introProcess");
        assertTrue(processInstance.isEnded());

        // Check for variable
        HistoricVariableInstance historicVariable = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(processInstance.getId())
                .singleResult();
        assertEquals("variablePresent", historicVariable.getVariableName());
        assertEquals(false, historicVariable.getValue());

        // Add a variable
        processInstance = runtimeService.startProcessInstanceByKey("introProcess",
                Collections.singletonMap("introVariable", (Object) "a test intro value"));
        List<HistoricVariableInstance> historicVariables = historyService
                .createHistoricVariableInstanceQuery()
                .processInstanceId(processInstance.getId())
                .orderByVariableName()
                .asc()
                .list();

        assertEquals("introVariable", historicVariables.get(0).getVariableName());
        assertEquals("a test intro value", historicVariables.get(0).getValue());
        assertEquals("variablePresent", historicVariables.get(1).getVariableName());
        assertEquals(true, historicVariables.get(1).getValue());

        processEngine.close();
    }

}
