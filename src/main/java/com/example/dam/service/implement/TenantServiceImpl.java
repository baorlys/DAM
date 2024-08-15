package com.example.dam.service.implement;

import com.example.dam.global.random.RandomId;
import com.example.dam.model.Tenant;
import com.example.dam.repository.TenantRepository;
import com.example.dam.service.TenantService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TenantServiceImpl implements TenantService {
    TenantRepository tenantRepository;

    @Override
    public void createTenant(String tenantName) {
        Tenant tenant = new Tenant();
        tenant.setName(tenantName);
        tenantRepository.save(tenant);
    }

    @Override
    public void deleteTenant(String tenantId) {

    }

    @Override
    public void switchTenant(String tenantId) {

    }

    boolean isTenantExist(String tenantName) {
        return tenantRepository.existsByName(tenantName);
    }

}
