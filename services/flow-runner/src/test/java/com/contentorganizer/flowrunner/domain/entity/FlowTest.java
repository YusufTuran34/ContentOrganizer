package com.contentorganizer.flowrunner.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@DisplayName("Flow Entity Tests")
class FlowTest {

    private Flow flow;
    private FlowStep step1;
    private FlowStep step2;
    private FlowTrigger trigger;

    @BeforeEach
    void setUp() {
        flow = new Flow("test-flow", "Test flow description");
        flow.setVersion("1.0");
        flow.setEnabled(true);
        
        // Create test steps
        step1 = new FlowStep();
        step1.setId("step1");
        step1.setName("Test Step 1");
        step1.setType(FlowStep.StepType.SERVICE_CALL);
        
        step2 = new FlowStep();
        step2.setId("step2");
        step2.setName("Test Step 2");
        step2.setType(FlowStep.StepType.LOG);
        
        // Create trigger
        trigger = new FlowTrigger();
        trigger.setType(FlowTrigger.TriggerType.CRON);
        trigger.setCronExpression("0 0 10 * * *");
        trigger.setEnabled(true);
        
        // Set global variables
        Map<String, Object> globalVars = new HashMap<>();
        globalVars.put("testVar", "testValue");
        flow.setGlobalVariables(globalVars);
    }

    @Test
    @DisplayName("Should create flow with basic properties")
    void shouldCreateFlowWithBasicProperties() {
        assertEquals("test-flow", flow.getName());
        assertEquals("Test flow description", flow.getDescription());
        assertEquals("1.0", flow.getVersion());
        assertTrue(flow.isEnabled());
        assertNotNull(flow.getGlobalVariables());
        assertEquals("testValue", flow.getGlobalVariables().get("testVar"));
    }

    @Test
    @DisplayName("Should add and retrieve steps")
    void shouldAddAndRetrieveSteps() {
        flow.addStep(step1);
        flow.addStep(step2);
        
        assertEquals(2, flow.getStepCount());
        assertEquals(step1, flow.getFirstStep());
        assertEquals(step1, flow.getStepById("step1"));
        assertEquals(step2, flow.getStepById("step2"));
        assertNull(flow.getStepById("nonexistent"));
    }

    @Test
    @DisplayName("Should remove step by ID")
    void shouldRemoveStepById() {
        flow.addStep(step1);
        flow.addStep(step2);
        
        assertTrue(flow.removeStep("step1"));
        assertEquals(1, flow.getStepCount());
        assertEquals(step2, flow.getFirstStep());
        
        assertFalse(flow.removeStep("nonexistent"));
    }

    @Test
    @DisplayName("Should get all step IDs")
    void shouldGetAllStepIds() {
        flow.addStep(step1);
        flow.addStep(step2);
        
        List<String> stepIds = flow.getStepIds();
        assertEquals(2, stepIds.size());
        assertTrue(stepIds.contains("step1"));
        assertTrue(stepIds.contains("step2"));
    }

    @Test
    @DisplayName("Should check if flow is scheduled")
    void shouldCheckIfFlowIsScheduled() {
        // Test with cron trigger
        flow.setTrigger(trigger);
        assertTrue(flow.isScheduled());
        
        // Test with manual trigger
        FlowTrigger manualTrigger = new FlowTrigger();
        manualTrigger.setType(FlowTrigger.TriggerType.MANUAL);
        flow.setTrigger(manualTrigger);
        assertFalse(flow.isScheduled());
        
        // Test with null trigger
        flow.setTrigger(null);
        assertFalse(flow.isScheduled());
    }

    @Test
    @DisplayName("Should handle null steps gracefully")
    void shouldHandleNullStepsGracefully() {
        flow.setSteps(null);
        
        assertEquals(0, flow.getStepCount());
        assertNull(flow.getFirstStep());
        assertNull(flow.getStepById("step1"));
        assertTrue(flow.getStepIds().isEmpty());
    }

    @Test
    @DisplayName("Should handle empty steps list")
    void shouldHandleEmptyStepsList() {
        flow.setSteps(new ArrayList<>());
        
        assertEquals(0, flow.getStepCount());
        assertNull(flow.getFirstStep());
        assertNull(flow.getStepById("step1"));
        assertTrue(flow.getStepIds().isEmpty());
    }

    @Test
    @DisplayName("Should set and get retry policy")
    void shouldSetAndGetRetryPolicy() {
        RetryPolicy retryPolicy = new RetryPolicy();
        retryPolicy.setMaxAttempts(3);
        retryPolicy.setStrategy(RetryPolicy.RetryStrategy.EXPONENTIAL_BACKOFF);
        
        flow.setDefaultRetryPolicy(retryPolicy);
        
        assertNotNull(flow.getDefaultRetryPolicy());
        assertEquals(3, flow.getDefaultRetryPolicy().getMaxAttempts());
        assertEquals(RetryPolicy.RetryStrategy.EXPONENTIAL_BACKOFF, flow.getDefaultRetryPolicy().getStrategy());
    }

    @Test
    @DisplayName("Should set and get YAML content")
    void shouldSetAndGetYamlContent() {
        String yamlContent = "name: test-flow\nsteps:\n  - id: step1";
        flow.setYamlContent(yamlContent);
        
        assertEquals(yamlContent, flow.getYamlContent());
    }

    @Test
    @DisplayName("Should generate proper toString representation")
    void shouldGenerateProperToString() {
        flow.setTrigger(trigger);
        flow.addStep(step1);
        
        String toString = flow.toString();
        
        assertTrue(toString.contains("test-flow"));
        assertTrue(toString.contains("1.0"));
        assertTrue(toString.contains("true"));
        assertTrue(toString.contains("1"));
        assertTrue(toString.contains("CRON"));
    }

    @Test
    @DisplayName("Should handle step with null ID")
    void shouldHandleStepWithNullId() {
        FlowStep nullIdStep = new FlowStep();
        nullIdStep.setId(null);
        nullIdStep.setName("Null ID Step");
        
        flow.addStep(nullIdStep);
        
        assertEquals(1, flow.getStepCount());
        assertNull(flow.getStepById(null));
    }

    @Test
    @DisplayName("Should handle cron trigger with empty expression")
    void shouldHandleCronTriggerWithEmptyExpression() {
        FlowTrigger emptyCronTrigger = new FlowTrigger();
        emptyCronTrigger.setType(FlowTrigger.TriggerType.CRON);
        emptyCronTrigger.setCronExpression("");
        emptyCronTrigger.setEnabled(true);
        
        flow.setTrigger(emptyCronTrigger);
        assertFalse(flow.isScheduled());
    }

    @Test
    @DisplayName("Should handle cron trigger with whitespace expression")
    void shouldHandleCronTriggerWithWhitespaceExpression() {
        FlowTrigger whitespaceCronTrigger = new FlowTrigger();
        whitespaceCronTrigger.setType(FlowTrigger.TriggerType.CRON);
        whitespaceCronTrigger.setCronExpression("   ");
        whitespaceCronTrigger.setEnabled(true);
        
        flow.setTrigger(whitespaceCronTrigger);
        assertFalse(flow.isScheduled());
    }
} 