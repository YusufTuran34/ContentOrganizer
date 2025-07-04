package com.contentorganizer.twitter.application.service;

import com.contentorganizer.twitter.web.dto.TweetRequest;
import com.contentorganizer.twitter.web.dto.TweetResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TwitterService {
    
    public TweetResponse postTweet(TweetRequest request) {
        log.info("Posting tweet: {}", request.getText());
        
        // Mock response for testing
        TweetResponse response = new TweetResponse();
        response.setTweetId("1234567890123456789");
        response.setStatus("SUCCESS");
        response.setMessage("Tweet posted successfully");
        
        return response;
    }
} 