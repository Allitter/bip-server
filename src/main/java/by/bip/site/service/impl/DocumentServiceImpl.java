package by.bip.site.service.impl;

import by.bip.site.model.Document;
import by.bip.site.repository.DocumentRepository;
import by.bip.site.service.AbstractCrudService;
import by.bip.site.service.DocumentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class DocumentServiceImpl extends AbstractCrudService<Document> implements DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentServiceImpl(DocumentRepository documentRepository) {
        super(documentRepository);
        this.documentRepository = documentRepository;
    }

    @Override
    public List<Document> findByName(String name) {
        return documentRepository.findDocumentsByOriginNameContaining(name);
    }

    @Override
    public Long removeByOriginName(String name) {
        return documentRepository.deleteByOriginName(name);
    }
}
