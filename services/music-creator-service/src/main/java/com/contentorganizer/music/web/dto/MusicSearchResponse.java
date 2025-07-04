package com.contentorganizer.music.web.dto;

import lombok.Data;
import java.util.List;

@Data
public class MusicSearchResponse {
    private List<MusicTrack> tracks;
    private String message;
    
    @Data
    public static class MusicTrack {
        private String id;
        private String name;
        private String artist;
        private String audioUrl;
        private Integer duration;
    }
} 