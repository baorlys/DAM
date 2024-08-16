package com.example.dam.controller;

import com.example.dam.dto.FolderDTO;
import com.example.dam.input.FolderInput;
import com.example.dam.input.ShareFolderInput;
import com.example.dam.input.TenantUserInput;
import com.example.dam.model.Folder;
import com.example.dam.service.FolderService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/folders")
@AllArgsConstructor
public class FolderController {
    private final FolderService folderService;
    @GetMapping("{id}")
    public ResponseEntity<List<FolderDTO>> getSpaceFolders(@RequestParam(required = false, defaultValue = "0") String pageNum,
                                                           @RequestParam(required = false, defaultValue = "10") String pageSize,
                                                           @RequestParam(required = false) String sortBy,
                                                           @RequestBody @NonNull UUID id
    ) {
        List<FolderDTO> dto = folderService.getAccessibleFolders(id, pageSize, pageNum, sortBy);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createFolder(@RequestBody FolderInput input) throws IOException {
        Folder folder = folderService.createFolder(
                input.getUserId(), input.getFolderName(), input.getTenantId(), input.getSpaceId(), input.getParentId());
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }

    @PostMapping("{id}")
    public ResponseEntity shareFolder(@PathVariable UUID id, @RequestBody ShareFolderInput input) {
        Folder folder = folderService.shareFolder(input.getEmail(), id, input.getRoleId(), input.getTenantId());
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteFolder(@PathVariable UUID id) {
        folderService.deleteFolder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
