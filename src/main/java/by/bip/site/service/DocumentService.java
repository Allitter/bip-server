package by.bip.site.service;

import by.bip.site.model.Document;

import java.util.List;

public interface DocumentService extends CrudService<Document> {

    List<Document> findByName(String name);

    Long removeByOriginName(String name);
}
