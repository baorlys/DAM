package com.example.dam.input;

import com.example.dam.model.UserFolder;
import lombok.Data;

@Data
public class ShareFolderInput {
    String email;
    UserFolder.Access access;
}
