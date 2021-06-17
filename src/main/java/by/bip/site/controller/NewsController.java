package by.bip.site.controller;

import by.bip.site.dto.NewsDto;
import by.bip.site.model.NewsItem;
import by.bip.site.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;
    private final ModelMapper modelMapper;

    @GetMapping()
    public List<NewsDto> getNews() {
        return newsService.findAll().stream()
                .map(newsItem -> modelMapper.map(newsItem, NewsDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping()
    public NewsDto addNews(@RequestBody NewsDto newsDto) {
        NewsItem newsItem = modelMapper.map(newsDto, NewsItem.class);
        newsItem = newsService.add(newsItem);
        return modelMapper.map(newsItem, NewsDto.class);
    }

    @DeleteMapping("/{id}")
    public NewsDto deleteNews(@PathVariable long id) {
        NewsItem newsItem = newsService.remove(id);
        return modelMapper.map(newsItem, NewsDto.class);
    }

    @GetMapping("/{id}")
    public NewsDto getNewsById(@PathVariable long id) {
        NewsItem newsItem = newsService.findById(id);
        return modelMapper.map(newsItem, NewsDto.class);
    }

    @PutMapping("/{id}")
    public NewsDto updateNews(@PathVariable long id, @RequestBody NewsDto newsDto) {
        newsDto.setId(id);
        NewsItem newsItem = modelMapper.map(newsDto, NewsItem.class);
        newsItem = newsService.update(newsItem);
        return modelMapper.map(newsItem, NewsDto.class);
    }
}
