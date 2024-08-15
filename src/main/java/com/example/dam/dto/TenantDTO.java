package com.example.dam.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class TenantDTO {
    UUID id;
    String name;
    List<AssetDTO> assets;
}
