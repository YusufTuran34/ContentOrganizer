package com.contentorganizer.aichat.application.service;

import com.contentorganizer.aichat.web.dto.ChatRequest;
import com.contentorganizer.aichat.web.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    
    public ChatResponse processQuickChat(ChatRequest request) {
        log.info("Processing quick chat request: {}", request.getPrompt());
        
        // Mock response for testing
        ChatResponse response = new ChatResponse();
        response.setTitle("AI ile Otomatik Video Üretimi");
        response.setDescription("Bu videoda yapay zeka teknolojilerini kullanarak nasıl otomatik video üretimi yapabileceğinizi öğreneceksiniz.");
        response.setTweet("🤖 Yapay Zeka ile otomatik video üretimi nasıl yapılır? Bu videoda tüm detayları öğrenin! #AI #VideoProduction #ContentCreation");
        response.setFullResponse("Başlık: AI ile Otomatik Video Üretimi\nAçıklama: Bu videoda yapay zeka teknolojilerini kullanarak nasıl otomatik video üretimi yapabileceğinizi öğreneceksiniz.\nTweet: 🤖 Yapay Zeka ile otomatik video üretimi nasıl yapılır? Bu videoda tüm detayları öğrenin! #AI #VideoProduction #ContentCreation");
        
        return response;
    }
} 