package net.cloudburo.task.intro;

import org.apache.camel.LoggingLevel;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntroTask implements JavaDelegate {

    Logger logger = LoggerFactory.getLogger(IntroTask.class);

    public void execute(DelegateExecution execution) {
        if (execution.hasVariable("introVariable")) {
            logger.info( "IntroVariable available with value " + execution.getVariable("intro"));
            execution.setVariable("variablePresent", true);
        } else {
            logger.info("IntroVariable not available");
            execution.setVariable("variablePresent", false);
        }
    }
}
