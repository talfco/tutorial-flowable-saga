package net.cloudburo.task;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class IntroTask implements JavaDelegate {

    public void execute(DelegateExecution execution) {
        if (execution.hasVariable("introVariable")) {
            System.out.println("IntroVariable available with value " + execution.getVariable("intro"));
            execution.setVariable("variablePresent", true);
        } else {
            System.out.println("IntroVariable not available");
            execution.setVariable("variablePresent", false);
        }
    }
}
