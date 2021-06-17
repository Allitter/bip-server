package by.bip.site.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewsDto {
    private Long id;
    private String title;
    private String shortDesc;
    private String content;
    private String previewImage;
    private LocalDateTime createTime;
    private LocalDateTime lastUpdateTime;
}
