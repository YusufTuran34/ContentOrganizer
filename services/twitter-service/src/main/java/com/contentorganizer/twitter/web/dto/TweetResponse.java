package com.contentorganizer.twitter.web.dto;

import lombok.Data;

@Data
public class TweetResponse {
    private String tweetId;
    private String status;
    private String message;
} 