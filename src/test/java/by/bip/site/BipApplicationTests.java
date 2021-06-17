package by.bip.site;

import by.bip.site.dto.SectionDto;
import by.bip.site.model.Page;
import by.bip.site.model.Section;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class BipApplicationTests {
    @Test
    void modelMapperTest() {
        ModelMapper modelMapper = new ModelMapper();
        Page page = Page.builder()
                .id(1L)
                .content("content")
                .createTime(LocalDateTime.now())
                .lastUpdateTime(LocalDateTime.now())
                .name("page 1")
                .build();


        Section section = Section.builder()
                .id(1L)
                .title("title")
                .pages(List.of(page))
                .build();

        page.setSection(section);


        SectionDto sectionDto = new SectionDto();

        System.out.println(modelMapper.map(section, SectionDto.class));
    }

}
