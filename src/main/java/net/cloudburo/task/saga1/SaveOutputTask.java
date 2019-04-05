package net.cloudburo.task.saga1;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class SaveOutputTask implements JavaDelegate {

    private Logger logger = LoggerFactory.getLogger(SaveOutputTask.class);

    @SuppressWarnings("unchecked")
    @Override
    public void execute(DelegateExecution execution) {
        Map<String, String> outputMap = (Map<String, String>) execution.getVariable("outputMap");
        outputMap.put("outputValue", (String) execution.getVariable("camelBody"));
        logger.info("CamelBody: " + execution.getVariable("camelBody"));
    }
}
