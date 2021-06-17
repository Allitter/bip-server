package by.bip.site.service;

import by.bip.site.model.Document;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    Document saveFile(MultipartFile multipartFile);

    void removeFile(long id);

    List<Document> findByName(String name);

    Resource getById(long id);
}
