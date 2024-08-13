package com.example.dam.service.transform;

import com.example.dam.enums.ResourceType;

import java.util.EnumMap;
import java.util.Map;

public class TransformFactory {
    private TransformFactory() {
        // Hide the constructor
    }
    static final Map<ResourceType, ITransformable> transformMap = new EnumMap<>(ResourceType.class);

    static {
        transformMap.put(ResourceType.IMAGE, new ImageTransform());
        transformMap.put(ResourceType.VIDEO, new VideoTransform());
    }

    public static ITransformable getTransform(ResourceType type) {
        return transformMap.get(type);
    }

}
