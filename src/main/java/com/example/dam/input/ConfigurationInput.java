package com.example.dam.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigurationInput {
    String tenantId;
    String apiKey;
    String secretKey;
}
