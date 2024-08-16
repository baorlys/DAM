package com.example.dam.service;

import com.example.dam.dto.TenantDTO;
import com.example.dam.exception.ExistsRecord;

import java.util.UUID;

public interface TenantService {
    UUID createTenant(String tenantName) throws ExistsRecord;
    void deleteTenant(String tenantId);
    TenantDTO switchTenant(String tenantId);
}
