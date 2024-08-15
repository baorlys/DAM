package com.example.dam.service.thumbnail;

import com.example.dam.enums.ResourceType;

import java.util.EnumMap;
import java.util.Map;

public class ThumbnailFactory {
    private ThumbnailFactory() {
        // Hide the constructor
    }
    static final Map<ResourceType, IGenerateThumbnail> thumbnailMap = new EnumMap<>(ResourceType.class);

    static {
        thumbnailMap.put(ResourceType.IMAGE, new ImageThumbnail());
        thumbnailMap.put(ResourceType.VIDEO, new VideoThumbnail());
        thumbnailMap.put(ResourceType.FILE, new DocumentThumbnail());
    }

    public static IGenerateThumbnail getGenerate(ResourceType type) {
        return thumbnailMap.get(type);
    }
}
