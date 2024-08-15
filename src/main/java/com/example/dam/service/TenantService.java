package com.example.dam.service;

public interface TenantService {
    void createTenant(String tenantName);
    void deleteTenant(String tenantId);
    void switchTenant(String tenantId);
}
