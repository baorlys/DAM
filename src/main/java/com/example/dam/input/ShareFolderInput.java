package com.example.dam.input;

import com.example.dam.model.UserFolder;
import lombok.Data;

import java.util.UUID;

@Data
public class ShareFolderInput {
    String email;
    UUID roleId;
    UUID tenantId;
}
