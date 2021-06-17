package by.bip.site.service.impl;

import by.bip.site.exception.ResourceNotFoundException;
import by.bip.site.model.Page;
import by.bip.site.model.Section;
import by.bip.site.repository.PageRepository;
import by.bip.site.repository.SectionRepository;
import by.bip.site.service.AbstractCrudService;
import by.bip.site.service.PageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class PageServiceImpl extends AbstractCrudService<Page> implements PageService {
    private static final String DEFAULT_SECTION_TITLE = "default";
    private final PageRepository pageRepository;
    private final SectionRepository sectionRepository;

    public PageServiceImpl(PageRepository pageRepository, SectionRepository sectionRepository) {
        super(pageRepository);
        this.pageRepository = pageRepository;
        this.sectionRepository = sectionRepository;
    }

    @Override
    public List<Page> findRemoved() {
        return pageRepository.recycleBin();
    }

    @Override
    public Page add(Page model) {
        Section section = model.getSection() == null || model.getSection().getId() == null
                ? sectionRepository.findFirstByTitle(DEFAULT_SECTION_TITLE).orElseThrow()
                : sectionRepository.findById(model.getSection().getId()).orElseThrow(ResourceNotFoundException::new);

        model.setSection(section);
        model.setCreateTime(LocalDateTime.now());
        return pageRepository.save(model);
    }

    @Override
    public Page update(Page model) {
        model.setLastUpdateTime(LocalDateTime.now());
        Page page = pageRepository.findById(model.getId()).orElseThrow(ResourceNotFoundException::new);
        if (StringUtils.isNotBlank(model.getName())) {
            page.setName(model.getName());
        }
        if (StringUtils.isNotBlank(model.getContent())) {
            page.setContent(model.getContent());
        }
        if (StringUtils.isNotBlank(model.getShortDesc())) {
            page.setShortDesc(model.getShortDesc());
        }
        if (model.getSection() != null && model.getSection().getId() != null) {
            Long sectionId = model.getSection().getId();
            Section section = sectionRepository.findById(sectionId).orElseThrow(ResourceNotFoundException::new);
            page.setSection(section);
        }

        return super.update(page);
    }

    @Override
    public List<Page> findBySectionId(Long sectionId) {
        Section section = sectionRepository.findById(sectionId).orElseThrow(ResourceNotFoundException::new);
        return pageRepository.findBySection(section);
    }

    @Override
    public Page remove(long id) {
        Page page = pageRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        pageRepository.softDelete(id);
        return page;
    }
}
