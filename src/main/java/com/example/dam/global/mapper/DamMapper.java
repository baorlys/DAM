package com.example.dam.global.mapper;

import com.example.dam.dto.AssetDTO;
import com.example.dam.global.service.CommonService;
import com.example.dam.model.Asset;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
public class DamMapper extends ModelMapper {
    ObjectMapper objectMapper;

    public AssetDTO mapAsset(Asset asset) throws JsonProcessingException {
        AssetDTO dto = this.map(asset, AssetDTO.class);
        dto.setThumbnailUrl(asset.getThumbnailPath());
        Map<String, String> metadata = objectMapper.readValue(asset.getMetadata(), new TypeReference<>() {
        });
        dto.setType(CommonService.findResourceType(metadata.get("resource_type")));
        dto.setSize(Integer.parseInt(metadata.get("size")));
        dto.setExtensionFile(CommonService.findExtensionFile(metadata.get("extension")));
        dto.setOriginalFilename(metadata.get("origin_name"));
        return dto;
    }
}
