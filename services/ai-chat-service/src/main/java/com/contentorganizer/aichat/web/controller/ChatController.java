package com.contentorganizer.aichat.web.controller;

import com.contentorganizer.aichat.application.service.ChatService;
import com.contentorganizer.aichat.web.dto.ChatRequest;
import com.contentorganizer.aichat.web.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    
    private final ChatService chatService;
    
    @PostMapping("/quick")
    public ResponseEntity<ChatResponse> quickChat(@RequestBody ChatRequest request) {
        ChatResponse response = chatService.processQuickChat(request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("AI Chat Service is running!");
    }
} 