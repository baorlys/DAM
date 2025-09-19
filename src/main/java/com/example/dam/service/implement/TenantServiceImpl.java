package com.example.dam.service.implement;

import com.example.dam.dto.AssetDTO;
import com.example.dam.dto.TenantDTO;
import com.example.dam.exception.ExistsRecord;
import com.example.dam.exception.NotFoundException;
import com.example.dam.global.mapper.DamMapper;
import com.example.dam.global.service.CommonService;
import com.example.dam.model.Tenant;
import com.example.dam.repository.AssetRepository;
import com.example.dam.repository.TenantRepository;
import com.example.dam.service.TenantService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TenantServiceImpl implements TenantService {
    TenantRepository tenantRepository;
    AssetRepository assetRepository;
    DamMapper damMapper;



    @Override
    public UUID createTenant(String tenantName) throws ExistsRecord {
        boolean isExist = isTenantExist(tenantName);
        CommonService.throwIsExist(isExist, "Tenant already exist");

        Tenant tenant = new Tenant();
        tenant.setName(tenantName);
        tenantRepository.save(tenant);
        return tenant.getId();
    }

    @Override
    public void deleteTenant(String tenantId) throws NotFoundException {
        Tenant readyDel = getTenant(tenantId);
        tenantRepository.delete(readyDel);
    }

    @Override
    public TenantDTO switchTenant(String tenantId) throws NotFoundException {
        Tenant tenant = getTenant(tenantId);

        AssetDTO[] assets = damMapper.map(assetRepository.findAllByTenantId(tenant.getId()), AssetDTO[].class);
        TenantDTO tenantDTO = damMapper.map(tenant, TenantDTO.class);
        tenantDTO.setAssets(List.of(assets));

        return tenantDTO;
    }

    @Override
    public Tenant getTenant(String tenantId) throws NotFoundException {
        return tenantRepository.findById(UUID.fromString(tenantId))
                .orElseThrow(() -> new NotFoundException("Tenant not found"));
    }

    boolean isTenantExist(String tenantName) {
        return tenantRepository.existsByName(tenantName);
    }

}
