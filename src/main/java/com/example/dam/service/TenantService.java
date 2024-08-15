package com.example.dam.service;

import com.example.dam.dto.TenantDTO;
import com.example.dam.exception.ExistsRecord;

public interface TenantService {
    void createTenant(String tenantName) throws ExistsRecord;
    void deleteTenant(String tenantId);
    TenantDTO switchTenant(String tenantId);
}
