package com.example.dam.controller;

import com.example.dam.dto.TenantDTO;
import com.example.dam.service.TenantService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
@Validated
public class UserController {
    TenantService tenantService;

    @GetMapping("/{tenantId}")
    public ResponseEntity<TenantDTO> switchTenant(@PathVariable String tenantId) {
        return ResponseEntity.ok(tenantService.switchTenant(tenantId));
    }
}
