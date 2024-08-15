package com.example.dam.service;

public interface TenantService {
    void createTenant(String tenantId);
    void deleteTenant(String tenantId);
    void switchTenant(String tenantId);
}
