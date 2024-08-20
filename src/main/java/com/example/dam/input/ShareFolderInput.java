package com.example.dam.input;

import lombok.Data;

import java.util.UUID;

@Data
public class ShareFolderInput {
    String email;
    UUID roleId;
}
