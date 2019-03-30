package net.cloudburo.task;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

import java.util.Map;

public class SaveOutputTask implements JavaDelegate {
        @SuppressWarnings("unchecked")
        @Override
        public void execute(DelegateExecution execution) {
            Map<String, String> outputMap = (Map<String, String>) execution.getVariable("outputMap");
            outputMap.put("outputValue", (String) execution.getVariable("camelBody"));
            System.out.println("CamelBody: " + (String) execution.getVariable("camelBody"));
        }
}
