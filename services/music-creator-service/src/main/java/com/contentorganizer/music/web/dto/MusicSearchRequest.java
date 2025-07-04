package com.contentorganizer.music.web.dto;

import lombok.Data;
import java.util.List;

@Data
public class MusicSearchRequest {
    private List<String> tags;
    private Integer targetDuration;
} 