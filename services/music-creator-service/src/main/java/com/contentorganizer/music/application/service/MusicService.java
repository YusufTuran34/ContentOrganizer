package com.contentorganizer.music.application.service;

import com.contentorganizer.music.web.dto.MusicSearchRequest;
import com.contentorganizer.music.web.dto.MusicSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class MusicService {
    
    public MusicSearchResponse searchMusic(MusicSearchRequest request) {
        log.info("Searching music with tags: {}, target duration: {}", request.getTags(), request.getTargetDuration());
        
        // Mock response for testing
        MusicSearchResponse response = new MusicSearchResponse();
        response.setMessage("Music search completed successfully");
        
        MusicSearchResponse.MusicTrack track = new MusicSearchResponse.MusicTrack();
        track.setId("12345");
        track.setName("Chill Lo-Fi Beats");
        track.setArtist("Lo-Fi Artist");
        track.setAudioUrl("https://example.com/music/chill-lofi.mp3");
        track.setDuration(300);
        
        response.setTracks(Arrays.asList(track));
        
        return response;
    }
} 