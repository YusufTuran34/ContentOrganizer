package com.contentorganizer.music.web.controller;

import com.contentorganizer.music.application.service.MusicService;
import com.contentorganizer.music.web.dto.MusicSearchRequest;
import com.contentorganizer.music.web.dto.MusicSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class MusicController {
    
    private final MusicService musicService;
    
    @PostMapping
    public ResponseEntity<MusicSearchResponse> searchMusic(@RequestBody MusicSearchRequest request) {
        MusicSearchResponse response = musicService.searchMusic(request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Music Creator Service is running!");
    }
} 