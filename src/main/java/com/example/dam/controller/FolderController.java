package com.example.dam.controller;

import com.example.dam.dto.FolderDTO;
import com.example.dam.input.FolderInput;
import com.example.dam.input.ShareFolderInput;
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
    public ResponseEntity<List<FolderDTO>> getSpaceFolders(@RequestParam(required = false, defaultValue = "0") int pageNum,
                                                           @RequestParam(required = false, defaultValue = "10") int pageSize,
                                                           @RequestParam(required = false) String sortBy,
                                                           @RequestBody @NonNull UUID id
    ) {
        List<FolderDTO> dto = folderService.getAccessibleFolders(id, pageSize, pageNum, sortBy);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FolderDTO> createFolder(
            @RequestHeader UUID tenantId,
            @RequestHeader UUID spaceId,
            @RequestHeader UUID userId,
            @RequestBody FolderInput input) throws IOException {
        FolderDTO dto = folderService.createFolder(userId, input.getFolderName(), tenantId, spaceId, input.getParentId());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("{id}")
    public ResponseEntity shareFolder(@PathVariable UUID id,
                                      @RequestHeader UUID tenantId,
                                      @RequestBody ShareFolderInput input) {
        FolderDTO dto = folderService.shareFolder(input.getEmail(), id, input.getRoleId(), tenantId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteFolder(@PathVariable UUID id) {
        folderService.deleteFolder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
