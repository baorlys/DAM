package com.example.dam.input;

import lombok.Data;

import java.util.UUID;

@Data
public class TenantUserInput {
    UUID tenantId;
    UUID userId;
}
