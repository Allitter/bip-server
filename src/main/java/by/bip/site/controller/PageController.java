package by.bip.site.controller;

import by.bip.site.dto.PageDto;
import by.bip.site.model.Page;
import by.bip.site.model.Section;
import by.bip.site.service.PageService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pages")
public class PageController {
    private final PageService pageService;
    private final ModelMapper modelMapper;

    public PageController(PageService pageService, ModelMapper modelMapper) {
        this.pageService = pageService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<PageDto> getPages() {
        return pageService.findAll().stream()
                .map(page -> modelMapper.map(page, PageDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/removed")
    public List<PageDto> getRemovedPages() {
        return pageService.findRemoved().stream()
                .map(page -> modelMapper.map(page, PageDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PageDto findById(@PathVariable Long id) {
        Page page = pageService.findById(id);
        return modelMapper.map(page, PageDto.class);
    }

    @PostMapping()
    public PageDto addPage(@RequestBody PageDto pageDto) {
        Page page = modelMapper.map(pageDto, Page.class);
        page.setSection(new Section(pageDto.getSectionId()));
        page = pageService.add(page);
        return modelMapper.map(page, PageDto.class);
    }

    @PutMapping("/{id}")
    public PageDto updatePage(@RequestBody PageDto pageDto, @PathVariable long id) {
        pageDto.setId(id);
        Page page = modelMapper.map(pageDto, Page.class);
        page.setSection(new Section(pageDto.getSectionId()));
        page = pageService.update(page);
        return modelMapper.map(page, PageDto.class);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePage(@PathVariable long id) {
        pageService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
