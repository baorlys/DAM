package com.example.dam.controller;

import com.example.dam.input.FolderInput;
import com.example.dam.input.ShareFolderInput;
import com.example.dam.model.Folder;
import com.example.dam.service.FolderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("api/folders")
@AllArgsConstructor
public class FolderController {
    private final FolderService folderService;

    @GetMapping("{id}")
    public ResponseEntity getFolderByID(@PathVariable UUID id) {
        Folder folder = folderService.getFolderById(id);
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createFolder(@RequestBody FolderInput input) throws IOException {
        Folder folder = folderService.createFolder(
                input.getUserId(), input.getFolderName(), input.getSpaceId(), input.getParentId());
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }

    @PostMapping("{id}")
    public ResponseEntity shareFolder(@PathVariable UUID id, @RequestBody ShareFolderInput input) {
        Folder folder = folderService.shareFolder(input.getEmail(), id, input.getAccess());
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteFolder(@PathVariable UUID id) {
        folderService.deleteFolder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
