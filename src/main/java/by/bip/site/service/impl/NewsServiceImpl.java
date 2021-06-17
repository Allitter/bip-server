package by.bip.site.service.impl;

import by.bip.site.exception.ResourceNotFoundException;
import by.bip.site.model.NewsItem;
import by.bip.site.repository.NewsRepository;
import by.bip.site.service.AbstractCrudService;
import by.bip.site.service.NewsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class NewsServiceImpl extends AbstractCrudService<NewsItem> implements NewsService {
    private final NewsRepository newsRepository;

    public NewsServiceImpl(NewsRepository newsRepository) {
        super(newsRepository);
        this.newsRepository = newsRepository;
    }

    @Override
    public NewsItem update(NewsItem model) {
        NewsItem newsItem = newsRepository.findById(model.getId()).orElseThrow(ResourceNotFoundException::new);
        if (StringUtils.isNotBlank(model.getContent())) {
            newsItem.setContent(model.getContent());
        }
        if (StringUtils.isNotBlank(model.getPreviewImage())) {
            newsItem.setPreviewImage(model.getPreviewImage());
        }
        if (StringUtils.isNotBlank(model.getTitle())) {
            newsItem.setTitle(model.getTitle());
        }
        if (StringUtils.isNotBlank(model.getShortDesc())) {
            newsItem.setShortDesc(model.getShortDesc());
        }
        newsItem.setLastUpdateTime(LocalDateTime.now());

        return super.update(newsItem);
    }

    @Override
    public NewsItem add(NewsItem model) {
        model.setCreateTime(LocalDateTime.now());
        return super.add(model);
    }

    @Override
    public List<NewsItem> findAll() {
        return newsRepository.findByOrderByCreateTimeDesc();
    }
}

