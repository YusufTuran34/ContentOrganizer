package com.contentorganizer.youtube.application.service;

import com.contentorganizer.youtube.web.dto.VideoUploadRequest;
import com.contentorganizer.youtube.web.dto.VideoUploadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class YouTubeService {
    
    public VideoUploadResponse uploadVideo(VideoUploadRequest request) {
        log.info("Uploading video: {} with title: {}", request.getVideoPath(), request.getTitle());
        
        // Mock response for testing
        VideoUploadResponse response = new VideoUploadResponse();
        response.setVideoId("dQw4w9WgXcQ");
        response.setStatus("SUCCESS");
        response.setMessage("Video uploaded successfully to YouTube");
        
        return response;
    }
} 