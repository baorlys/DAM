package com.example.dam.controller;

import com.example.dam.dto.TenantDTO;
import com.example.dam.exception.ExistsRecord;
import com.example.dam.service.TenantService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @PostMapping("/create-tenant")
    public ResponseEntity<UUID> createTenant(@RequestParam String tenantName) throws ExistsRecord {
        return ResponseEntity.ok(tenantService.createTenant(tenantName));
    }

    @DeleteMapping("/delete-tenant")
    public ResponseEntity<Void> deleteTenant(@RequestParam String tenantId) {
        tenantService.deleteTenant(tenantId);
        return ResponseEntity.ok().build();
    }

}
