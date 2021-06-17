package by.bip.site.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PageDto {
    private long id;
    private String name;
    private String shortDesc;
    private LocalDateTime createTime;
    private LocalDateTime lastUpdateTime;
    private String content;
    private Long sectionId;
}
