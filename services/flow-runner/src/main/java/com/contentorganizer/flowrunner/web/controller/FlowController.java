package com.contentorganizer.flowrunner.web.controller;

import com.contentorganizer.flowrunner.application.service.FlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/flows")
@RequiredArgsConstructor
public class FlowController {
    
    private final FlowService flowService;
    
    @PostMapping("/execute")
    public ResponseEntity<String> executeFlow(@RequestBody String flowYaml) {
        String result = flowService.executeFlow(flowYaml);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/execute/{flowId}")
    public ResponseEntity<Map<String, Object>> executeFlowById(@PathVariable String flowId) {
        Map<String, Object> response = new HashMap<>();
        response.put("flowId", flowId);
        response.put("status", "executing");
        response.put("timestamp", LocalDateTime.now());
        response.put("message", "Flow execution started for: " + flowId);
        
        // Mock execution for now
        try {
            // Simulate flow execution
            Thread.sleep(1000);
            response.put("status", "completed");
            response.put("result", "Flow executed successfully");
        } catch (InterruptedException e) {
            response.put("status", "failed");
            response.put("error", e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "flow-runner");
        response.put("timestamp", LocalDateTime.now());
        response.put("version", "1.0.0");
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/test-flow")
    public ResponseEntity<Map<String, Object>> testFlow() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Test flow orchestration endpoint is working!");
        response.put("timestamp", LocalDateTime.now());
        response.put("availableFlows", new String[]{"test-flow-orchestration"});
        
        return ResponseEntity.ok(response);
    }
} 