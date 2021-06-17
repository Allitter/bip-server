package by.bip.site.repository;

import by.bip.site.model.Document;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends CrudRepository<Document, Long> {

    List<Document> findDocumentsByOriginNameContaining(String name);

    Long deleteByOriginName(String name);
}
