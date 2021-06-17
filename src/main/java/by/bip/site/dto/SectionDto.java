package by.bip.site.dto;

import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class SectionDto {
    private Long id;
    private String title;
    private Set<PageDto> pages;

    public SectionDto() {
        pages = new LinkedHashSet<>();
    }

    public void setPages(Set<PageDto> pages) {
        if (pages != null) {
            this.pages = pages;
        }
    }
}
