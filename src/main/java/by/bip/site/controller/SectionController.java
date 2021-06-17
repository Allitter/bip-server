package by.bip.site.controller;

import by.bip.site.dto.PageDto;
import by.bip.site.dto.SectionDto;
import by.bip.site.model.Page;
import by.bip.site.model.Section;
import by.bip.site.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sections")
@RequiredArgsConstructor
public class SectionController {
    private final SectionService sectionService;
    private final ModelMapper modelMapper;

    @GetMapping()
    public List<SectionDto> getSections() {
        return sectionService.findAll().stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private SectionDto map(Section section) {
        SectionDto dto = modelMapper.map(section, SectionDto.class);
        if (CollectionUtils.isNotEmpty(dto.getPages())) {
            Set<PageDto> pages = section.getPages().stream()
                    .map(page -> modelMapper.map(page, PageDto.class))
                    .collect(Collectors.toSet());
            dto.setPages(pages);
        }
        return dto;
    }

    private Section map(SectionDto dto) {
        Section section = modelMapper.map(dto, Section.class);
        if (CollectionUtils.isNotEmpty(dto.getPages())) {
            List<Page> pages = dto.getPages().stream()
                    .map(pageDto -> modelMapper.map(pageDto, Page.class))
                    .collect(Collectors.toList());
            section.setPages(pages);
        }
        return section;
    }

    @PostMapping()
    public SectionDto addSection(@RequestBody SectionDto sectionDto) {
        Section section = map(sectionDto);
        section = sectionService.add(section);
        return map(section);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSection(@PathVariable long id) {
        sectionService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public SectionDto getSection(@PathVariable long id) {
        Section section = sectionService.findById(id);
        return map(section);
    }

    @PutMapping("/{id}")
    public SectionDto updateSection(@PathVariable long id, @RequestBody SectionDto sectionDto) {
        sectionDto.setId(id);
        Section section = map(sectionDto);
        section = sectionService.update(section);
        return map(section);
    }
}
