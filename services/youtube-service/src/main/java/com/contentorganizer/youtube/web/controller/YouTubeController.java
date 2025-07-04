package com.contentorganizer.youtube.web.controller;

import com.contentorganizer.youtube.application.service.YouTubeService;
import com.contentorganizer.youtube.web.dto.VideoUploadRequest;
import com.contentorganizer.youtube.web.dto.VideoUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class YouTubeController {
    
    private final YouTubeService youTubeService;
    
    @PostMapping
    public ResponseEntity<VideoUploadResponse> uploadVideo(@RequestBody VideoUploadRequest request) {
        VideoUploadResponse response = youTubeService.uploadVideo(request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("YouTube Service is running!");
    }
} 