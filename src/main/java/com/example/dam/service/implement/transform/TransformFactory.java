package com.example.dam.service.implement.transform;

import com.example.dam.enums.ResourceType;

import java.util.Map;

public class TransformFactory {
    private TransformFactory() {
        // Hide the constructor
    }
    static final Map<ResourceType, ITransformable> transformMap = Map.of(
            ResourceType.IMAGE, new ImageTransform(),
            ResourceType.VIDEO, new VideoTransform()
    );

    public static ITransformable getTransform(ResourceType type) {
        return transformMap.get(type);
    }

}
