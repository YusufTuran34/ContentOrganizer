package com.contentorganizer.flowrunner.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DisplayName("FlowStep Entity Tests")
class FlowStepTest {

    private FlowStep flowStep;
    private ServiceCall serviceCall;

    @BeforeEach
    void setUp() {
        flowStep = new FlowStep();
        flowStep.setId("test-step");
        flowStep.setName("Test Step");
        flowStep.setType(FlowStep.StepType.SERVICE_CALL);
        flowStep.setNextStepId("next-step");
        
        // Create service call
        serviceCall = new ServiceCall();
        serviceCall.setServiceName("test-service");
        serviceCall.setMethod("POST");
        serviceCall.setEndpoint("/api/test");
        
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        serviceCall.setHeaders(headers);
        
        Map<String, Object> body = new HashMap<>();
        body.put("testParam", "testValue");
        serviceCall.setBody(body);
        
        flowStep.setServiceCall(serviceCall);
    }

    @Test
    @DisplayName("Should create flow step with basic properties")
    void shouldCreateFlowStepWithBasicProperties() {
        assertEquals("test-step", flowStep.getId());
        assertEquals("Test Step", flowStep.getName());
        assertEquals(FlowStep.StepType.SERVICE_CALL, flowStep.getType());
        assertEquals("next-step", flowStep.getNextStepId());
    }

    @Test
    @DisplayName("Should set and get service call")
    void shouldSetAndGetServiceCall() {
        assertNotNull(flowStep.getServiceCall());
        assertEquals("test-service", flowStep.getServiceCall().getServiceName());
        assertEquals("POST", flowStep.getServiceCall().getMethod());
        assertEquals("/api/test", flowStep.getServiceCall().getEndpoint());
        assertEquals("application/json", flowStep.getServiceCall().getHeaders().get("Content-Type"));
        assertEquals("testValue", ((Map<String, Object>) flowStep.getServiceCall().getBody()).get("testParam"));
    }

    @Test
    @DisplayName("Should set and get retry policy")
    void shouldSetAndGetRetryPolicy() {
        RetryPolicy retryPolicy = new RetryPolicy();
        retryPolicy.setMaxAttempts(3);
        retryPolicy.setStrategy(RetryPolicy.RetryStrategy.EXPONENTIAL_BACKOFF);
        retryPolicy.setInitialDelayMs(1000);
        
        flowStep.setRetryPolicy(retryPolicy);
        
        assertNotNull(flowStep.getRetryPolicy());
        assertEquals(3, flowStep.getRetryPolicy().getMaxAttempts());
        assertEquals(RetryPolicy.RetryStrategy.EXPONENTIAL_BACKOFF, flowStep.getRetryPolicy().getStrategy());
        assertEquals(1000, flowStep.getRetryPolicy().getInitialDelayMs());
    }

    @Test
    @DisplayName("Should set and get timeout")
    void shouldSetAndGetTimeout() {
        flowStep.setTimeoutSeconds(30);
        assertEquals(30, flowStep.getTimeoutSeconds());
    }

    @Test
    @DisplayName("Should set and get continue on failure")
    void shouldSetAndGetContinueOnFailure() {
        flowStep.setContinueOnFailure(true);
        assertTrue(flowStep.isContinueOnFailure());
        
        flowStep.setContinueOnFailure(false);
        assertFalse(flowStep.isContinueOnFailure());
    }

    @Test
    @DisplayName("Should set and get on success actions")
    void shouldSetAndGetOnSuccessActions() {
        List<String> onSuccessActions = List.of("log-success", "notify-admin");
        flowStep.setOnSuccess(onSuccessActions);
        
        assertEquals(onSuccessActions, flowStep.getOnSuccess());
    }

    @Test
    @DisplayName("Should set and get on failure actions")
    void shouldSetAndGetOnFailureActions() {
        List<String> onFailureActions = List.of("log-error", "retry");
        flowStep.setOnFailure(onFailureActions);
        
        assertEquals(onFailureActions, flowStep.getOnFailure());
    }

    @Test
    @DisplayName("Should set and get variables")
    void shouldSetAndGetVariables() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("message", "Test message");
        variables.put("level", "INFO");
        
        flowStep.setVariables(variables);
        
        assertNotNull(flowStep.getVariables());
        assertEquals("Test message", flowStep.getVariables().get("message"));
        assertEquals("INFO", flowStep.getVariables().get("level"));
    }

    @Test
    @DisplayName("Should set and get conditions")
    void shouldSetAndGetConditions() {
        StepCondition condition = new StepCondition();
        condition.setField("response.status");
        condition.setOperator(StepCondition.ConditionOperator.EQUALS);
        condition.setExpectedValue("200");
        condition.setNextStepIdOnTrue("success-step");
        condition.setNextStepIdOnFalse("error-step");
        
        List<StepCondition> conditions = List.of(condition);
        flowStep.setConditions(conditions);
        
        assertNotNull(flowStep.getConditions());
        assertEquals(1, flowStep.getConditions().size());
        assertEquals("response.status", flowStep.getConditions().get(0).getField());
        assertEquals(StepCondition.ConditionOperator.EQUALS, flowStep.getConditions().get(0).getOperator());
    }

    @Test
    @DisplayName("Should check if step is service call")
    void shouldCheckIfStepIsServiceCall() {
        flowStep.setType(FlowStep.StepType.SERVICE_CALL);
        assertTrue(flowStep.isServiceCall());
        
        flowStep.setType(FlowStep.StepType.LOG);
        assertFalse(flowStep.isServiceCall());
    }

    @Test
    @DisplayName("Should check if step is conditional")
    void shouldCheckIfStepIsConditional() {
        // Test with conditions
        StepCondition condition = new StepCondition();
        condition.setField("response.status");
        flowStep.setConditions(List.of(condition));
        assertTrue(flowStep.isConditional());
        
        // Test without conditions
        flowStep.setConditions(null);
        assertFalse(flowStep.isConditional());
        
        // Test with empty conditions
        flowStep.setConditions(List.of());
        assertFalse(flowStep.isConditional());
    }

    @Test
    @DisplayName("Should check if step has multiple outcomes")
    void shouldCheckIfStepHasMultipleOutcomes() {
        // Test with multiple onSuccess actions
        flowStep.setOnSuccess(List.of("action1", "action2"));
        assertTrue(flowStep.hasMultipleOutcomes());
        
        // Test with multiple onFailure actions
        flowStep.setOnSuccess(null);
        flowStep.setOnFailure(List.of("action1", "action2"));
        assertTrue(flowStep.hasMultipleOutcomes());
        
        // Test with single actions
        flowStep.setOnFailure(List.of("action1"));
        assertFalse(flowStep.hasMultipleOutcomes());
    }

    @Test
    @DisplayName("Should generate proper toString representation")
    void shouldGenerateProperToString() {
        String toString = flowStep.toString();
        
        assertTrue(toString.contains("test-step"));
        assertTrue(toString.contains("Test Step"));
        assertTrue(toString.contains("SERVICE_CALL"));
        assertTrue(toString.contains("timeoutSeconds"));
    }

    @Test
    @DisplayName("Should handle null service call")
    void shouldHandleNullServiceCall() {
        flowStep.setServiceCall(null);
        assertNull(flowStep.getServiceCall());
    }

    @Test
    @DisplayName("Should handle null conditions")
    void shouldHandleNullConditions() {
        flowStep.setConditions(null);
        assertNull(flowStep.getConditions());
        assertFalse(flowStep.isConditional());
    }

    @Test
    @DisplayName("Should handle null variables")
    void shouldHandleNullVariables() {
        flowStep.setVariables(null);
        assertNull(flowStep.getVariables());
    }

    @Test
    @DisplayName("Should handle null on success actions")
    void shouldHandleNullOnSuccessActions() {
        flowStep.setOnSuccess(null);
        assertNull(flowStep.getOnSuccess());
    }

    @Test
    @DisplayName("Should handle null on failure actions")
    void shouldHandleNullOnFailureActions() {
        flowStep.setOnFailure(null);
        assertNull(flowStep.getOnFailure());
    }

    @Test
    @DisplayName("Should use constructor with parameters")
    void shouldUseConstructorWithParameters() {
        FlowStep step = new FlowStep("step-id", "Step Name", FlowStep.StepType.LOG);
        
        assertEquals("step-id", step.getId());
        assertEquals("Step Name", step.getName());
        assertEquals(FlowStep.StepType.LOG, step.getType());
        assertEquals(30, step.getTimeoutSeconds()); // Default value
        assertFalse(step.isContinueOnFailure()); // Default value
    }

    @Test
    @DisplayName("Should set and get description")
    void shouldSetAndGetDescription() {
        flowStep.setDescription("Test description");
        assertEquals("Test description", flowStep.getDescription());
    }
} 