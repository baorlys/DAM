package com.example.dam.service;

import com.example.dam.dto.TenantDTO;
import com.example.dam.exception.ExistsRecord;
import com.example.dam.exception.NotFoundException;
import com.example.dam.model.Tenant;

import java.util.UUID;

public interface TenantService {
    UUID createTenant(String tenantName) throws ExistsRecord;
    void deleteTenant(String tenantId) throws NotFoundException;
    TenantDTO switchTenant(String tenantId) throws NotFoundException;

    Tenant getTenant(String tenantId) throws NotFoundException;
}
