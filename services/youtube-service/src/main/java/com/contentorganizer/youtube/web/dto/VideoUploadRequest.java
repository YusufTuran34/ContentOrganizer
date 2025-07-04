package com.contentorganizer.youtube.web.dto;

import lombok.Data;

@Data
public class VideoUploadRequest {
    private String videoPath;
    private String title;
    private String description;
} 