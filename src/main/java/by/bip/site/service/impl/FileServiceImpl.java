package by.bip.site.service.impl;

import by.bip.site.exception.ServiceException;
import by.bip.site.model.Document;
import by.bip.site.service.DocumentService;
import by.bip.site.service.FileService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

@Transactional(isolation = Isolation.REPEATABLE_READ)
public class FileServiceImpl implements FileService {
    private final File uploadDirectory;
    private final DocumentService documentService;

    public FileServiceImpl(String uploadDirectory, DocumentService documentService) {
        this.uploadDirectory = new File(uploadDirectory);
        this.documentService = documentService;
    }

    @Override
    public Document saveFile(MultipartFile multipartFile) {
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String descriptiveImage = getDescriptiveImageByExtension(extension);
        String name = String.format("%s.%s", UUID.randomUUID(), extension);
        File destination = new File(uploadDirectory, name);
        try (OutputStream outputStream = IOUtils.buffer(new FileOutputStream(destination))) {
            InputStream inputStream = IOUtils.buffer(multipartFile.getInputStream());
            IOUtils.copy(inputStream, outputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        }


        Document document = new Document(name, multipartFile.getOriginalFilename(), descriptiveImage);
        documentService.add(document);
        return document;
    }

    private String getDescriptiveImageByExtension(String extension) {
        if (StringUtils.containsAny(extension, "png", "svg", "jpg", "jpeg", "gif")) {
            return "descriptives/image.svg";
        }
        if (extension.contains("doc")) {
            return "descriptives/docx.svg";
        }
        if (extension.contains("pdf")) {
            return "descriptives/pdf.svg";
        }

        return "descriptives/document.svg";
    }

    @Override
    public void removeFile(long id) {
        Document document = documentService.remove(id);
        File file = new File(uploadDirectory, document.getName());
        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Document> findByName(String name) {
        return documentService.findByName(name);
    }

    @Override
    public Resource getById(long id) {
        Document document = documentService.findById(id);
        File file = new File(uploadDirectory, document.getName());
        return new FileSystemResource(file);
    }
}
