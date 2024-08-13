package com.example.dam.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "storage")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StorageProperties {
    String path;
    String transformPath;
    String thumbnailPath;
    String pathFormat;
}
