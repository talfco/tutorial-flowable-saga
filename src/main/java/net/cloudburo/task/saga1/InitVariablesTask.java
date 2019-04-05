package net.cloudburo.task.saga1;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class InitVariablesTask implements JavaDelegate {

    private Logger logger = LoggerFactory.getLogger(InitVariablesTask.class);

    public void execute(DelegateExecution execution) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("input","{ 'name' : 'Hello World'}");
        Map<String, String> outputMap = new HashMap<String,String>();
        variables.put("outputMap",outputMap);
        execution.setVariables(variables);
        logger.info("Input Variable Set To "+execution.getVariable("input"));
    }

}
