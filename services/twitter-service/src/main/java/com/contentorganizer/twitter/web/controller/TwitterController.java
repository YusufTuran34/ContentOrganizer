package com.contentorganizer.twitter.web.controller;

import com.contentorganizer.twitter.application.service.TwitterService;
import com.contentorganizer.twitter.web.dto.TweetRequest;
import com.contentorganizer.twitter.web.dto.TweetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tweet")
@RequiredArgsConstructor
public class TwitterController {
    
    private final TwitterService twitterService;
    
    @PostMapping
    public ResponseEntity<TweetResponse> postTweet(@RequestBody TweetRequest request) {
        TweetResponse response = twitterService.postTweet(request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Twitter Service is running!");
    }
} 