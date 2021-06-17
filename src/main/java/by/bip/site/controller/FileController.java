package by.bip.site.controller;

import by.bip.site.dto.DocumentDto;
import by.bip.site.model.Document;
import by.bip.site.service.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;
    private final ModelMapper modelMapper;

    public FileController(FileService fileService, ModelMapper modelMapper) {
        this.fileService = fileService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<DocumentDto> findFiles(@RequestParam(required = false, defaultValue = "") String name) {
        return fileService.findByName(name).stream()
                .map(document -> modelMapper.map(document, DocumentDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    public DocumentDto uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        Document document = fileService.saveFile(multipartFile);
        return modelMapper.map(document, DocumentDto.class);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFile(@PathVariable Long id) {
        fileService.removeFile(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Resource findFile(@PathVariable Long id) {
        return fileService.getById(id);
    }
}
