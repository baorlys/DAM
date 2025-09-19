package com.example.dam.service.implement;

import com.example.dam.enums.ResourceType;
import com.example.dam.enums.TransformVariable;
import com.example.dam.service.HandleAssetService;
import com.example.dam.service.thumbnail.IGenerateThumbnail;
import com.example.dam.service.thumbnail.ThumbnailFactory;
import com.example.dam.service.transform.ITransformable;
import com.example.dam.service.transform.TransformFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

@Service
public class HandleAssetServiceImpl implements HandleAssetService {
    @Override
    public void transform(ResourceType type, String inputPath, String outputPath, Map<TransformVariable, String> options)
            throws IOException, InterruptedException {
        ITransformable transformer = TransformFactory.getTransform(type);
        transformer.transform(inputPath, outputPath, options);
    }

    @Override
    public void generateThumbnail(ResourceType type, String filePath, String outputPath, int scaleTarget)
            throws IOException, InterruptedException {
        IGenerateThumbnail thumbnailGenerator = ThumbnailFactory.getGenerate(type);
        thumbnailGenerator.generateThumbnail(filePath, outputPath, scaleTarget);
    }

    @Override
    public Map<TransformVariable, String> convertToTransformVariable(Map<String, String> options) {
        Map<TransformVariable, String> transformVariables = new EnumMap<>(TransformVariable.class);
        for (Map.Entry<String, String> entry : options.entrySet()) {
            transformVariables.put(TransformVariable.fromShortCut(entry.getKey()), entry.getValue());
        }
        return transformVariables;
    }
}
