package by.bip.site.service.impl;

import by.bip.site.exception.ResourceNotFoundException;
import by.bip.site.model.Section;
import by.bip.site.repository.SectionRepository;
import by.bip.site.service.AbstractCrudService;
import by.bip.site.service.SectionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class SectionServiceImpl extends AbstractCrudService<Section> implements SectionService {
    private static final String DEFAULT_SECTION_TITLE = "default";
    private final SectionRepository sectionRepository;

    public SectionServiceImpl(SectionRepository sectionRepository) {
        super(sectionRepository);
        this.sectionRepository = sectionRepository;
    }

    @Override
    public Section update(Section model) {
        Section section = sectionRepository.findById(model.getId()).orElseThrow(ResourceNotFoundException::new);
        throwNotFoundIfDefault(section);
        if (StringUtils.isNotBlank(model.getTitle())) {
            section.setTitle(model.getTitle());
        }

        return super.update(model);
    }

    @Override
    public Section remove(long id) {
        Section section = sectionRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        throwNotFoundIfDefault(section);
        Section defaultSection = getDefault();
        section.getPages().forEach(page -> page.setSection(defaultSection));
        sectionRepository.softDelete(section.getId());
        return section;
    }

    private void throwNotFoundIfDefault(Section section) {
        if (section.equals(getDefault())) {
            throw new ResourceNotFoundException();
        }
    }

    private Section getDefault() {
        return sectionRepository.findFirstByTitle(DEFAULT_SECTION_TITLE).orElseThrow();
    }
}
