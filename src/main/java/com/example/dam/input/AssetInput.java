package com.example.dam.input;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@Data
public class AssetInput {
    MultipartFile file;
    Map<String, String> options;
}
