package by.bip.site.service;

import by.bip.site.model.Page;

import java.util.List;

public interface PageService extends CrudService<Page> {

    List<Page> findBySectionId(Long sectionId);

    List<Page> findRemoved();
}
