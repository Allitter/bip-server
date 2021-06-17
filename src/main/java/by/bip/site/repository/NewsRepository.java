package by.bip.site.repository;

import by.bip.site.model.NewsItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NewsRepository extends CrudRepository<NewsItem, Long> {

    List<NewsItem> findByOrderByCreateTimeDesc();
}
