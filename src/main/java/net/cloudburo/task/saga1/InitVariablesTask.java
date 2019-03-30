package net.cloudburo.task.saga1;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

import java.util.HashMap;
import java.util.Map;

public class InitVariablesTask implements JavaDelegate {

    public void execute(DelegateExecution execution) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("input","{ 'name' : 'Hello World'}");
        Map<String, String> outputMap = new HashMap<String,String>();
        variables.put("outputMap",outputMap);
        execution.setVariables(variables);
        System.out.println("Input Variable Set To "+execution.getVariable("input"));
    }

}
