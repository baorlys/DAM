package com.example.dam.input;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
@Data
public class AssetInput {
    MultipartFile file;
    ConfigurationInput configuration;
    Map options;
}
