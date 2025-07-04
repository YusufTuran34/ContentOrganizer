package com.contentorganizer.youtube.web.dto;

import lombok.Data;

@Data
public class VideoUploadResponse {
    private String videoId;
    private String status;
    private String message;
} 